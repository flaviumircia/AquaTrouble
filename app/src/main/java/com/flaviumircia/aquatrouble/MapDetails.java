package com.flaviumircia.aquatrouble;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.adapter.damage_data.ExtendedData;
import com.flaviumircia.aquatrouble.adapter.damage_data.PostAdapter;
import com.flaviumircia.aquatrouble.map.math.PolygonCustomTitle;
import com.flaviumircia.aquatrouble.map.settings.MapPointCorrecter;
import com.flaviumircia.aquatrouble.map.settings.PolygonMarkerTitle;
import com.flaviumircia.aquatrouble.misc.PathReturner;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.retrofit.DamageDataApi;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.flaviumircia.aquatrouble.theme.ThemeModeChecker;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MapDetails extends AppCompatActivity implements ThemeModeChecker, MapPointCorrecter, PolygonMarkerTitle, PathReturner {
    private String neighborhood;
    private TextView title;
    private TextView total_text;
    private ImageButton back_arrow,search_button;
    private MapView map;
    private FolderOverlay kmlOverlay;
    private DamageDataApi myApi;
    private ZoomKmlStyler styler;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private final String file="LANGUAGE_PREF";

    private void setLanguage() {
        LanguageSetter languageSetter=new LanguageSetter();
        //set the language
        SharedPreferences sharedPreferences= this.getSharedPreferences(file, Context.MODE_PRIVATE);
        String language=sharedPreferences.getString("lang",null);
        languageSetter.setLocale(language,this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        neighborhood=selectedNeighborhood();

        setContentView(R.layout.activity_map_details);

        Retrofit retrofit= RetrofitClient.getInstance();
        myApi=retrofit.create(DamageDataApi.class);
        recyclerView=findViewById(R.id.street_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        title=findViewById(R.id.neighborhoodText);
        title.setText(neighborhood);
        total_text=findViewById(R.id.total_text);
        back_arrow=findViewById(R.id.backArrowMap);
        map = findViewById(R.id.zoomedMap);
        searchView=findViewById(R.id.searchView_map);
//        search_button=findViewById(R.id.search_button_map);
        KmlDocument kmlDocument=new KmlDocument();
        String pathToFile=return_the_path("codebeautify.kml");
        kmlDocument.parseKMLFile(new File(pathToFile));

        //map settings

        fetchData(neighborhood,map);

        onClick(back_arrow);
        setTheMap(kmlDocument);

        //get the kml document from the assets folder



    }

    private void onClick(ImageButton back_arrow) {
        this.back_arrow.setOnClickListener(view -> MapDetails.super.finish());
//        search_button.setOnClickListener(view_search->{
//            Intent myIntent=new Intent(this, Search.class);
//            myIntent.putExtra("sector",false);
//            myIntent.putExtra("neighborhood",neighborhood);
//            startActivity(myIntent);
//        });
    }

    private void fetchData(String neighborhood, MapView map) {
        compositeDisposable.add(myApi.getData(neighborhood)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> displayData(data,map))
        );
    }

    private void displayData(List<Data> data, MapView map) {
        PostAdapter adapter=new PostAdapter(this,data,map);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        ExtendedData extendedPostAdapter=new ExtendedData(data);
        total_text.setText(String.valueOf(extendedPostAdapter.getTheTotalDamage()));


    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    /**
     * Gets the neighborhood name from the Home activity
     * @return the neighborhood name if extras!=null
     *         or
     *         the String "Neighborhood not found" if the name doesn't exist
     */
    private String selectedNeighborhood() {
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            return extras.getString("neighborhood");
        }
        return "Neighborhood not found";
    }

    private GeoPoint centerOf()
    {   GeoPoint geoPoint;
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            double lat=extras.getDouble("lat");
            double lng=extras.getDouble("lng");
            geoPoint=new GeoPoint(lat,lng);
            return geoPoint;
        }
        return new GeoPoint(0,0);
    }
    private double [] getBounds(double offset)
    {   double []bounds =new double[4];
        Bundle savedBounds=getIntent().getExtras();
        if(savedBounds!=null)
        {
                bounds[0]=savedBounds.getDouble("north")+offset;
                bounds[1]= savedBounds.getDouble("south")-offset;
                bounds[2]=savedBounds.getDouble("west")-offset;
                bounds[3]=savedBounds.getDouble("east")+offset;
        }
        return bounds;
    }
    private void setTheMap(KmlDocument kmlDocument) {
        //window and system_mode initialize
        Window window=getWindow();
        int nightModeFlags=getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;

        //map contoller
        IMapController mapController = map.getController();

        //the startPoint of the map
        GeoPoint startPoint=centerOf();
        map.setTilesScaledToDpi(true);
        //custom styler
//        map.setTilesScaleFactor(1.2f);

        styler = new ZoomKmlStyler();

        //custom Polygon info class
        PolygonCustomTitle polygonCustomTitle=new PolygonCustomTitle();

        // map tile provider
        map.setTileSource(TileSourceFactory.MAPNIK);

        //map controller for setting the zoom on the map
        mapController.setZoom(14.00);

        //set the center of the map
        mapController.setCenter(startPoint);

        //hide the zoom in/out buttons of the map
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.setMaxZoomLevel(22.00);
        map.setMinZoomLevel(14.00);

        //set the pinch zoom
        map.setMultiTouchControls(true);

        //custom styler
        styler.setPolygonMiscInfo(polygonCustomTitle);

        //setting the custom map theme
        setCustomTheme(window,nightModeFlags);

        //set scrollable limits
        double [] bounds=getBounds(0.005);
        map.setScrollableAreaLimitLatitude(bounds[0],bounds[1],1);
        map.setScrollableAreaLimitLongitude(bounds[2],bounds[3],1);

        //upload the overlay on the osmdroid map
        kmlOverlay=(FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map,null,styler,kmlDocument);

        //getting the data from the object inside the kmlStyler class
        polygonCustomTitle=styler.getPolygonMiscInfo();

        //title for each polygon
        setPolygonMarkerTitle(polygonCustomTitle,nightModeFlags);

        map.getOverlays().add(kmlOverlay);
        //reload the map with the overlay
        map.invalidate();
    }
    public void onResume(){
        super.onResume();
        Log.d("MAP", "onResume: ");
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: ");
        if(requestCode==0 && resultCode==Activity.RESULT_OK && data!=null){
            double latitude=data.getDoubleExtra("latitude",0);
            double longitude=data.getDoubleExtra("longitude",0);
            IMapController mapController=map.getController();

            GeoPoint geoPoint=new GeoPoint(latitude,longitude);
            Marker marker=new Marker(map);
            marker.setPosition(geoPoint);
            marker.setIcon(getResources().getDrawable(R.drawable.ic_location));
            mapController.setCenter(geoPoint);
            map.getOverlays().add(marker);
        }
    }

    public void onPause(){
        super.onPause();
        Log.d("MAP", "onPause: ");

        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Configuration.getInstance().save(getApplicationContext(), prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }


    @Override
    public void setCustomTheme(Window window, int system_mode) {
        switch (system_mode) {
            case android.content.res.Configuration.UI_MODE_NIGHT_YES:
                styler.setAlphaValue("#1B");
                NightModeTiles nightModeTiles=new NightModeTiles("#414141");
                ColorMatrixColorFilter filter = nightModeTiles.getFilter();
                map.getOverlayManager().getTilesOverlay().setColorFilter(filter);
                break;
            case android.content.res.Configuration.UI_MODE_NIGHT_NO:
                styler.setAlphaValue("#6B");
                break;
            case android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED:
                styler.setAlphaValue("#1B");
                break;
        }
    }

    @Override
    public GeoPoint correctPolygonCenter(String polygon_title, GeoPoint default_geoPoint) {
        switch (polygon_title)
        {
            case "Aviatorilor":
                return new GeoPoint(44.463076, 26.080204);
            case "Chitila":
                return new GeoPoint(44.478563, 26.031868);
            case "Andronache":
                return new GeoPoint(44.476978, 26.146700);
            case "Pantelimon":
                return new GeoPoint(44.442995, 26.168011);
            case "Ghencea":
                return new GeoPoint(44.407679, 26.036413);
            case "Grozavesti":
                return new GeoPoint(44.442657, 26.066072);
            case "Stefan Cel Mare":
                return new GeoPoint(44.450020, 26.110795);
            case "Mosilor":
                return new GeoPoint(44.442245, 26.122989);
            case "Dorobanti":
                return new GeoPoint(44.456187, 26.090401);
            default: return default_geoPoint;
        }
    }

    @Override
    public void setPolygonMarkerTitle(PolygonCustomTitle customPolygonInfo, int system_mode) {
        for (int i=0;i<customPolygonInfo.getTitle().size();i++)
        {
            Marker marker=new Marker(map);

            //calling the method for normalizing the area between given integers
            customPolygonInfo.normalizeTheData(22,50);

            //converting area to only integers
            Double sizeOfText=new Double(customPolygonInfo.getArea().get(i));
            int sizeInt=sizeOfText.intValue();

            //marker customization
            marker.setTextLabelFontSize(sizeInt);

            if(system_mode==android.content.res.Configuration.UI_MODE_NIGHT_YES)
                marker.setTextLabelForegroundColor(Color.WHITE);
            else
                marker.setTextLabelForegroundColor(Color.BLACK);

            marker.setTextLabelBackgroundColor(Color.TRANSPARENT);
            marker.setTextIcon(customPolygonInfo.getTitle().get(i));

            marker.setPosition(correctPolygonCenter(customPolygonInfo.getTitle().get(i),customPolygonInfo.getThePoints().get(i)));

            //adding the overlay to the map
            map.getOverlays().add(marker);
        }
    }

    @Override
    public String return_the_path(String file_name) {
        File f = new File(this.getCacheDir()+"/"+file_name);
        if (!f.exists()) try {
            InputStream is = this.getAssets().open(file_name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) { throw new RuntimeException(e); }
        return f.getPath();
    }
}