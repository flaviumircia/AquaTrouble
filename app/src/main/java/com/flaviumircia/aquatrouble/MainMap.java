package com.flaviumircia.aquatrouble;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.MapInfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.flaviumircia.aquatrouble.databinding.ActivityMapBinding;
import com.flaviumircia.aquatrouble.menufragments.Home;
import com.flaviumircia.aquatrouble.menufragments.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainMap extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private ActivityMapBinding binding;
    //TODO: SharedPreferences for language and theme saving !!!
    //TODO: Pass data from this activity to the zoomed one with bundle (search on stackoverflow)
    //TODO: Search for a way to put Markers in the center of the polygon with null icon and title= name of the neighborhood. HINT: add empty icon in the center of the polygons with the name of them on it
    //TODO: Create the other fragments to choose between in the navigation menu bar (see if you can recreate one empty)
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the language
        SharedPreferences sharedPreferences=this.getSharedPreferences("pref",0);
        String language=sharedPreferences.getString("lang",null);
        LanguageSetter languageSetter=new LanguageSetter();
        languageSetter.setLocale(language,this);

        binding=ActivityMapBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new Home());
        binding.bottomNavigationView.setSelectedItemId(R.id.home_icon);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home_icon:
                    replaceFragment(new Home());
                    break;
                case R.id.settings_icon:
                    replaceFragment(new Settings());
                    break;
            }

            return true;
        });
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}