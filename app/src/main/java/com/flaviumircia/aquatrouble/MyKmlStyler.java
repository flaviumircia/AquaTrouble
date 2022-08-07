package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.flaviumircia.aquatrouble.area.CalculateAreaOfPoly;
import com.flaviumircia.aquatrouble.area.PolygonCustomTitle;

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
    private PolygonCustomTitle polygonCustomTitle;

    public MyKmlStyler(Context context)
    {
        this.theContext=context;
    }

    /**
     * Adding title to the polygon
     * @param polygonCustomTitle
     */
    public void setPolygonCustomTitle(PolygonCustomTitle polygonCustomTitle) {
        this.polygonCustomTitle = polygonCustomTitle;
    }

    /**
     * Getting the title from the polygon
     * @return title of the polygon
     */
    public PolygonCustomTitle getPolygonCustomTitle() {
        return polygonCustomTitle;
    }

    @Override
    public void onFeature(Overlay overlay, KmlFeature kmlFeature) {
    }

    @Override
    public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
    }

    @Override
    public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
    }

    /**
     * The method for customizing the polygon E.g: onClick, colour, coordinates etc
     *
     * @param polygon
     * @param kmlPlacemark
     * @param kmlPolygon
     */
    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        //setting the stroke and colour of the poly
        polygon.setStrokeWidth(5f);
        polygon.setFillColor(Color.parseColor("#6B0ffAff"));

        //Class for calculating the area of the polygon
        CalculateAreaOfPoly calculateAreaOfPoly=new CalculateAreaOfPoly(polygon.getActualPoints());

        //getting the title and center coordinates of each poly
        this.polygonCustomTitle.setTitle(polygon.getTitle());
        //calculates the center of the polygon and add it
        this.polygonCustomTitle.setThePoints(polygon.getBounds().getCenterWithDateLine());
        //add area of each polygon
        this.polygonCustomTitle.setArea(calculateAreaOfPoly.polyArea());

        //polygon on click listener
        polygon.setOnClickListener(new Polygon.OnClickListener() {
            @Override
            public boolean onClick(Polygon polygon, MapView mapView, GeoPoint eventPos) {

                //creating intent for activity
                Intent myIntent=new Intent(theContext,MapDetails.class);

                //passing data between activities
                myIntent.putExtra("neighborhood",polygon.getTitle());
                myIntent.putExtra("lat",polygon.getBounds().getCenterWithDateLine().getLatitude());
                myIntent.putExtra("lng",polygon.getBounds().getCenterWithDateLine().getLongitude());
                //flagging the new activity
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                //start the activity
                theContext.startActivity(myIntent);
                return true;
            }
        });

    }

    @Override
    public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

    }

}
