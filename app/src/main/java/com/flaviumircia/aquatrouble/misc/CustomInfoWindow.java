package com.flaviumircia.aquatrouble.misc;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flaviumircia.aquatrouble.R;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class CustomInfoWindow  extends InfoWindow {
    private TextView street_address,street_numbers;
    private String string_street_address,string_street_numbers;
    private Button button;
    private View main_view;
    public CustomInfoWindow(View v, MapView mapView,String string_street_address,String string_street_numbers) {
        super(v, mapView);
        this.string_street_address=string_street_address;
        this.string_street_numbers=string_street_numbers;
    }


    @Override
    public void onOpen(Object item) {
        closeAllInfoWindowsOn(mMapView);
        street_address=getView().findViewById(R.id.title_address_infoWindow);
        street_numbers=getView().findViewById(R.id.street_numbers_infoWindow);
        street_address.setText(string_street_address);
        street_numbers.setText(string_street_numbers);
        button=getView().findViewById(R.id.button);
        button.setOnClickListener(view2->{
            onClose();
        });
    }

    @Override
    public void onClose() {
        close();
    }


}
