package com.flaviumircia.aquatrouble;

import android.view.View;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class MyInfoWindow extends InfoWindow {

    public MyInfoWindow(View v, MapView mapView) {
        super(v, mapView);

    }

    @Override
    public void onOpen(Object item) {

    }

    @Override
    public void onClose() {

    }
}
