package com.flaviumircia.aquatrouble.menufragments.mapfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.MyKmlStyler;
import com.flaviumircia.aquatrouble.NightModeTiles;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.map.math.PolygonCustomTitle;
import com.flaviumircia.aquatrouble.map.settings.MapPointCorrecter;
import com.flaviumircia.aquatrouble.map.settings.PolygonMarkerTitle;
import com.flaviumircia.aquatrouble.misc.PathReturner;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OsmdroidMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OsmdroidMap extends Fragment implements MapPointCorrecter, ThemeModeChecker, PolygonMarkerTitle, PathReturner {
    private final String file="LANGUAGE_PREF";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HomeMap";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MapView map;
    private MyKmlStyler styler;
    private FolderOverlay kmlOverlay;

    public OsmdroidMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OsmdroidMap.
     */
    // TODO: Rename and change types and number of parameters
    public static OsmdroidMap newInstance(String param1, String param2) {
        OsmdroidMap fragment = new OsmdroidMap();
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
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(requireContext(),file);
        preferenceLanguageSetter.setTheLanguage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_osmdroid_map,container,false);

        //context for osmdroid
        Context ctx = getActivity();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        //get the kml document from the assets folder
        KmlDocument kmlDocument=new KmlDocument();
        String pathToFile=return_the_path("codebeautify.kml");
        kmlDocument.parseKMLFile(new File(pathToFile));
        //getting the R.id for the MapView
        map = v.findViewById(R.id.osmdroidMap);

        //map settings
        setTheMap(kmlDocument);

        //return the view
        return v;
    }
    private void setTheMap(KmlDocument kmlDocument) {
        Window window=getActivity().getWindow();
        int nightModeFlags=getContext().getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        // map tile provider
        map.setTileSource(TileSourceFactory.MAPNIK);
        //map controller for setting the zoom on the map
        IMapController mapController = map.getController();
        mapController.setZoom(14.00);
        //starting point (default) of the map
        GeoPoint startPoint = new GeoPoint(44.426164962,26.102332924);
        //set the center of the map
        mapController.setCenter(startPoint);
        //hide the zoom in/out buttons of the map
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.setMinZoomLevel(14.00);
        map.setMaxZoomLevel(21.00);
        //set the pinch zoom
        map.setMultiTouchControls(true);

        //set scrollable limits
        map.setScrollableAreaLimitLatitude(44.549523,44.335193,1);
        map.setScrollableAreaLimitLongitude(25.928859,26.242889,1);

        //Class for adding the markers in the center of the polygons
        PolygonCustomTitle polygonCustomTitle=new PolygonCustomTitle();

        //Styler of the map
        styler=new MyKmlStyler(getContext());

        setCustomTheme(window,nightModeFlags);
        styler.setPolygonMiscInfo(polygonCustomTitle);

        //get the kml overlay
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
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Configuration.getInstance().load(getActivity(), PreferenceManager.getDefaultSharedPreferences(getActivity()));
        map.setTilesScaledToDpi(false);
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Configuration.getInstance().save(getActivity(), prefs);
        map.setTilesScaledToDpi(false);
            map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
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
    public void setPolygonMarkerTitle(PolygonCustomTitle customPolygonInfo,int system_mode) {
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
    //return the path of the file from assets
    //neccessary for the overlay of the shapes on the osmdroid
    @Override
    public String return_the_path(String file_name) {
        File f = new File(getActivity().getCacheDir()+"/"+file_name);
        if (!f.exists()) try {
            InputStream is = getActivity().getAssets().open(file_name);
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