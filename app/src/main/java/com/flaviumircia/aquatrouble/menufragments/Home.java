package com.flaviumircia.aquatrouble.menufragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.MyKmlStyler;
import com.flaviumircia.aquatrouble.PermissionCheck;
import com.flaviumircia.aquatrouble.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //from activity
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

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //instantiate the mapView
        View v=inflater.inflate(R.layout.fragment_home,container,false);

        //permissions for using the osmdroid map
        checkThePermissions();

        //context for osmdroid
        Context ctx = getActivity();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        //get the kml document from the assets folder
        KmlDocument kmlDocument=new KmlDocument();
        String pathToFile=returnPath("codebeautify.kml");
        kmlDocument.parseKMLFile(new File(pathToFile));

        //set the content view of the activity
        map =v.findViewById(R.id.osmdroidMap);

        //map settings
        setTheMap(kmlDocument);

        //return the viewgroup
        return v;
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
        map.setMinZoomLevel(13.00);

        //set the pinch zoom
        map.setMultiTouchControls(true);

        //set scrollable limits
        map.setScrollableAreaLimitLatitude(44.539523,44.335193,1);
        map.setScrollableAreaLimitLongitude(25.928859,26.242889,1);

        //upload the overlay on the osmdroid map
        //TODO: Check Styler overriding custom
        KmlFeature.Styler styler = new MyKmlStyler(getActivity());
        Marker m=new Marker(map);
        m.setTextLabelBackgroundColor(Color.TRANSPARENT);
        m.setTextLabelForegroundColor(Color.BLACK);
        m.setTextIcon("COX");

        //get the kml overlay
        kmlOverlay=(FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map,null,styler,kmlDocument);
        map.getOverlays().add(kmlOverlay);
        map.getOverlays().add(m);

        //reload the map with the overlay
        map.invalidate();
    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Configuration.getInstance().load(getActivity(), PreferenceManager.getDefaultSharedPreferences(getActivity()));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Configuration.getInstance().save(getActivity(), prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    //return the path of the file from assets
    //neccessary for the overlay of the shapes on the osmdroid
    private String returnPath(String name){
        File f = new File(getActivity().getCacheDir()+"/"+name);
        if (!f.exists()) try {
            InputStream is = getActivity().getAssets().open(name);
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
        permissionCheck.askPermissions(multiplePermissionLauncher,PERMISSIONS,getActivity());
    }
}