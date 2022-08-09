package com.flaviumircia.aquatrouble;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.area.PolygonCustomTitle;

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

public class MapDetails extends AppCompatActivity {
    private String neighborhood;
    private TextView title;
    private MapView map;
    private FolderOverlay kmlOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        neighborhood=selectedNeighborhood();

        setContentView(R.layout.activity_map_details);
        title=findViewById(R.id.neighborhoodText);
        title.setText(neighborhood);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FF7A9AA3"));
        //get the kml document from the assets folder
        KmlDocument kmlDocument=new KmlDocument();
        String pathToFile=returnPath("codebeautify.kml");
        kmlDocument.parseKMLFile(new File(pathToFile));
        map = findViewById(R.id.zoomedMap);

        //map settings
        setTheMap(kmlDocument);
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
                bounds[1]= savedBounds.getDouble("south")+offset;
                bounds[2]=savedBounds.getDouble("west")+offset;
                bounds[3]=savedBounds.getDouble("east")+offset;
        }
        return bounds;
    }
    private void setTheMap(KmlDocument kmlDocument) {

        // map tile provider
        map.setTileSource(TileSourceFactory.MAPNIK);

        //map controller for setting the zoom on the map
        IMapController mapController = map.getController();
        mapController.setZoom(16.00);

        //starting point (default) of the map
        GeoPoint startPoint =centerOf();

        //set the center of the map
        mapController.setCenter(startPoint);

        //hide the zoom in/out buttons of the map
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.setMinZoomLevel(16.00);

        //set the pinch zoom
        map.setMultiTouchControls(true);

        //custom styler
        ZoomKmlStyler styler = new ZoomKmlStyler();
        check_theme(styler);
        PolygonCustomTitle polygonCustomTitle=new PolygonCustomTitle();
        styler.setPolygonMiscInfo(polygonCustomTitle);

        //set scrollable limits
        double [] bounds=getBounds(0);
        map.setScrollableAreaLimitLatitude(bounds[0],bounds[1],1);
        map.setScrollableAreaLimitLongitude(bounds[2],bounds[3],1);

        //upload the overlay on the osmdroid map
        kmlOverlay=(FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map,null,styler,kmlDocument);

        //getting the data from the object inside the kmlStyler class
        polygonCustomTitle=styler.getPolygonMiscInfo();

        //title for each polygon
        neighborhood_marker_title(polygonCustomTitle);

        map.getOverlays().add(kmlOverlay);

        //reload the map with the overlay
        map.invalidate();
    }
    private String returnPath(String name){
        File f = new File(getApplicationContext().getCacheDir()+"/"+name);
        if (!f.exists()) try {
            InputStream is = getApplicationContext().getAssets().open(name);
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
    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Configuration.getInstance().save(getApplicationContext(), prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    private void neighborhood_marker_title(PolygonCustomTitle polygonCustomTitle) {
        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        for (int i=0;i<polygonCustomTitle.getTitle().size();i++)
        {
            Marker marker=new Marker(map);

            //calling the method for normalizing the area between given integers
            polygonCustomTitle.normalizeTheData(22,50);

            //converting area to only integers
            Double sizeOfText=new Double(polygonCustomTitle.getArea().get(i));
            int sizeInt=sizeOfText.intValue();
            //marker customizaiton
            marker.setTextLabelFontSize(sizeInt);
            if(nightModeFlags==android.content.res.Configuration.UI_MODE_NIGHT_YES)
            {
                marker.setTextLabelForegroundColor(Color.WHITE);

            } else {
                marker.setTextLabelForegroundColor(Color.BLACK);
            }
            marker.setTextLabelBackgroundColor(Color.TRANSPARENT);
            marker.setTextIcon(polygonCustomTitle.getTitle().get(i));

            marker.setPosition(correctCenter(polygonCustomTitle.getTitle().get(i),polygonCustomTitle.getThePoints().get(i)));

            //adding the overlay to the map
            map.getOverlays().add(marker);
        }
    }

    private GeoPoint correctCenter(String title,GeoPoint defaultCase) {
        switch (title)
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
            default: return defaultCase;
        }

    }
    private void check_theme(ZoomKmlStyler styler) {
        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
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
}