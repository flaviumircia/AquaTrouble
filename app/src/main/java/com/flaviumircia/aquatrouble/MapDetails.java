package com.flaviumircia.aquatrouble;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;

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
    private void setTheMap(KmlDocument kmlDocument) {
        // map tile provider
        map.setTileSource(TileSourceFactory.MAPNIK);
        //map controller for setting the zoom on the map
        IMapController mapController = map.getController();
        mapController.setZoom(16.00);

        //starting point (default) of the map

        GeoPoint startPoint =new GeoPoint(0,0);
        startPoint=centerOf();
        //set the center of the map
        mapController.setCenter(startPoint);

        //hide the zoom in/out buttons of the map
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.setMinZoomLevel(16.00);

        //set the pinch zoom
        map.setMultiTouchControls(true);

        //set scrollable limits
        map.setScrollableAreaLimitLatitude(44.539523,44.335193,1);
        map.setScrollableAreaLimitLongitude(25.928859,26.242889,1);

        //upload the overlay on the osmdroid map
        //TODO: Check Styler overriding custom
        KmlFeature.Styler styler = new ZoomKmlStyler();
//        Marker m=new Marker(map);
//        m.setTextLabelBackgroundColor(Color.TRANSPARENT);
//        m.setTextLabelForegroundColor(Color.BLACK);
        //get the kml overlay
        kmlOverlay=(FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map,null,styler,kmlDocument);

        map.getOverlays().add(kmlOverlay);
//        map.getOverlays().add(m);

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


}