package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlLineString;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.KmlPolygon;
import org.osmdroid.bonuspack.kml.KmlTrack;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

public class MyKmlStyler implements KmlFeature.Styler {
    private Context theContext;
    public MyKmlStyler(Context context)
    {
        this.theContext=context;
    }

    @Override
    public void onFeature(Overlay overlay, KmlFeature kmlFeature) {
//        overlay.
    }

    @Override
    public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

    }

    @Override
    public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
    }

    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {
        Log.d("TAG", "onPolygon: "+polygon.getTitle());
        polygon.setOnClickListener(new Polygon.OnClickListener() {
            @Override
            public boolean onClick(Polygon polygon, MapView mapView, GeoPoint eventPos) {
                Intent myIntent=new Intent(theContext,Eula.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                theContext.startActivity(myIntent);
                return true;
            }
        });
    }

    @Override
    public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

    }
}
