package com.flaviumircia.aquatrouble;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.Style;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.CustomZoomButtonsDisplay;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainMap extends AppCompatActivity {
    private MapView map;
    private FolderOverlay kmlOverlay;
    private ActivityResultContracts.RequestMultiplePermissions multiplePermissions;
    private ActivityResultLauncher<String[]> multiplePermissionLauncher;
    private final String [] PERMISSIONS={
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //permissions for using the osmdroid map
        checkThePermissions();

        //context for osmdroid
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        //get the kml document from the assets folder
        KmlDocument kmlDocument=new KmlDocument();
        String pathToFile=returnPath("codebeautify.kml");
        kmlDocument.parseKMLFile(new File(pathToFile));

        //set the content view of the activity
        setContentView(R.layout.activity_map);

        //instantiate the mapView
        map = (MapView) findViewById(R.id.osmdroidMap);

        //map settings
        setTheMap(kmlDocument);
    }

    private void setTheMap(KmlDocument kmlDocument) {
        // map tile provider
        map.setTileSource(TileSourceFactory.MAPNIK);

        //map controller for setting the zoom on the map
        IMapController mapController = map.getController();
        mapController.setZoom(13.00);

        //starting point (default) of the map
        GeoPoint startPoint = new GeoPoint(44.426164962,26.102332924);

        //set the center of the map
        mapController.setCenter(startPoint);

        //hide the zoom in/out buttons of the map
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);

        //set the pinch zoom
        map.setMultiTouchControls(true);

        //upload the overlay on the osmdroid map
        kmlOverlay=(FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map,null,null,kmlDocument);
        map.getOverlays().add(kmlOverlay);

        //reload the map with the overlay
        map.invalidate();
    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    //return the path of the file from assets
    //neccessary for the overlay of the shapes on the osmdroid
    private String returnPath(String name){
        File f = new File(getCacheDir()+"/"+name);
        if (!f.exists()) try {
            InputStream is = getAssets().open(name);
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

    //check the permissions method
    private void checkThePermissions(){
        multiplePermissions= new ActivityResultContracts.RequestMultiplePermissions();
        multiplePermissionLauncher= registerForActivityResult(multiplePermissions, isGranted->{
            Log.d("PERMISSIONS", "LAUNCHER RESULT: "+isGranted.toString());
            if(isGranted.containsValue(false))
            {
                Log.d("PERMISSIONS", "At least one permission was not granted, launching again... ");
                multiplePermissionLauncher.launch(PERMISSIONS);
            }
        });
        PermissionCheck permissionCheck=new PermissionCheck();
        permissionCheck.askPermissions(multiplePermissionLauncher,PERMISSIONS,getApplicationContext());
    }

}