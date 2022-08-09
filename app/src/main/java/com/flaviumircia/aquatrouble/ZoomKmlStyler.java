package com.flaviumircia.aquatrouble;

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
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

public class ZoomKmlStyler implements KmlFeature.Styler {
    private PolygonCustomTitle polygonMiscInfo;

    private String alphaValue;
    public ZoomKmlStyler()
    {
        alphaValue="#6B";
    }

    public String getAlphaValue() {
        return alphaValue;
    }

    public void setAlphaValue(String alphaValue) {
        this.alphaValue = alphaValue;
    }
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
        String polyColor=colorFormatter(kmlPlacemark.getExtendedData("fill"),this.alphaValue);

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
    /**
     * Method for extracting the color from the kml file
     * @return a string containing the color with the alpha=6B
     */
    private String colorFormatter(String extendedData,String alphaValue){

        if(extendedData==null)
        {
            return alphaValue+"0ffAff";}

        //setting the stroke and colour of the poly
        String[] formattedColor=extendedData.split("#",2);
        return alphaValue+formattedColor[1];
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
