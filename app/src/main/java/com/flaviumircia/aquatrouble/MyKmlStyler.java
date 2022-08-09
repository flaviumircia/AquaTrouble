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
    private PolygonCustomTitle polygonMiscInfo;
    private String alphaValue;
    public MyKmlStyler(Context context)
    {
        this.theContext=context;
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

    /**
     * Getting the title from the polygon
     * @return title of the polygon
     */
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

    /**
     * The method for customizing the polygon E.g: onClick, colour, coordinates etc
     *
     * @param polygon
     * @param kmlPlacemark
     * @param kmlPolygon
     */
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
                myIntent.putExtra("north",polygon.getBounds().getLatNorth());
                myIntent.putExtra("south",polygon.getBounds().getLatSouth());
                myIntent.putExtra("east",polygon.getBounds().getLonEast());
                myIntent.putExtra("west",polygon.getBounds().getLonWest());
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

    private void miscSettingsForPolygon(String title,GeoPoint center,double area)
    {
        //getting the title and center coordinates of each poly
        this.polygonMiscInfo.setTitle(title);
        //calculates the center of the polygon and add it
        this.polygonMiscInfo.setThePoints(center);
        //add area of each polygon
        this.polygonMiscInfo.setArea(area);

    }
}
