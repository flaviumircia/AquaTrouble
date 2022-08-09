package com.flaviumircia.aquatrouble;

import android.graphics.Color;
import android.util.Log;

import com.flaviumircia.aquatrouble.area.CalculateAreaOfPoly;
import com.flaviumircia.aquatrouble.area.PolygonCustomTitle;

import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlLineString;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.KmlPolygon;
import org.osmdroid.bonuspack.kml.KmlTrack;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

public class ZoomKmlStyler implements KmlFeature.Styler {
    private PolygonCustomTitle polygonMiscInfo;


    /**
     * Adding title to the polygon
     * @param polygonMiscInfo
     */
    public void setPolygonMiscInfo(PolygonCustomTitle polygonMiscInfo) {
        this.polygonMiscInfo = polygonMiscInfo;
    }
    public PolygonCustomTitle getPolygonMiscInfo() {
        return polygonMiscInfo;
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

    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        //getting individual color for each polygon
        String polyColor=colorFormatter(kmlPlacemark.getExtendedData("fill"));
        polygon.setFillColor(Color.parseColor(polyColor));
        //polygon stroke width
        polygon.setStrokeWidth(5.0f);

        //Class for calculating the area of the polygon
        CalculateAreaOfPoly calculateAreaOfPoly=new CalculateAreaOfPoly(polygon.getActualPoints());

        //some settings for the PolygonCustomTitle class
        miscSettingsForPolygon(polygon.getTitle(),polygon.getBounds().getCenterWithDateLine(),calculateAreaOfPoly.polyArea());

    }

    @Override
    public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

    }
    private String colorFormatter(String extendedData){

        if(extendedData==null) return "#6B0ffAff";

        //setting the stroke and colour of the poly
        String[] formattedColor=extendedData.split("#",2);
        String finalColor="#6B"+formattedColor[1];

        return finalColor;
    }

    private void miscSettingsForPolygon(String title, GeoPoint center, double area)
    {
        //getting the title and center coordinates of each poly
        this.polygonMiscInfo.setTitle(title);
        //calculates the center of the polygon and add it
        this.polygonMiscInfo.setThePoints(center);
        //add area of each polygon
        this.polygonMiscInfo.setArea(area);
    }

}
