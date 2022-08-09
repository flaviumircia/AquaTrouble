package com.flaviumircia.aquatrouble.area;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Collections;

public class PolygonCustomTitle {

    ArrayList<GeoPoint> geoPoints;
    ArrayList<String> title;
    ArrayList<Double> area;
    ArrayList<Double> normalized_area;
    double north,east,south,west;

    /**
     * Constructor getting the context and map from the parent activity
     * because I couldn't find a way to retrieve the polygon coordinates from activity (You need the override the KmlFeature class)
     */
    public PolygonCustomTitle()
    {   this.geoPoints=new ArrayList<>();
        this.title=new ArrayList<>();
        this.area=new ArrayList<>();
        this.normalized_area=new ArrayList<>();

    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    /**
     * Normalized area getter.
     * The real area getter is not created
     * @return the normalized area
     */
    public ArrayList<Double> getArea() {
        return normalized_area;
    }

    /**
     * Adding to the ArrayList the area of each polygon
     * @param area of each polygon
     */
    public void setArea(double area) {
        this.area.add(area);
    }

    /**
     * Adds to geoPoint ArrayList the points of each polygon
     * @param geoPoint the coordinates of the polygon
     */
    public void setThePoints(GeoPoint geoPoint)
    {
        this.geoPoints.add(geoPoint);
    }

    /**
     * Title of polygon getter
     * @return title
     */
    public ArrayList<String> getTitle() {
        return title;
    }

    /**
     * Title of polygon setter
     * @param title
     */
    public void setTitle(String title) {
        this.title.add(title);
    }

    /**
     * The getter for the geopoints of the polygons
     * @return arraylist of geopoints
     */
    public ArrayList<GeoPoint> getThePoints()
    {
        return this.geoPoints;
    }

    /**
     * Normalize the data from the area between [a,b],
     * Usefull for text size on the map
     * @param a lower bound of the interval
     * @param b higher bound of the interval
     */
    public void normalizeTheData(int a, int b)
    {
        double max= Collections.max(this.area);
        double min= Collections.min(this.area);

        for(int i=0;i<this.area.size();i++)
        {
            this.normalized_area.add(normalize(this.area.get(i),min,max,a,b));
        }
    }
    /**
     * Calculates a value between [a,b], given the precondition that value
     * is between min and max.
     * If value == max returns b
     * else if value == min returns a
     * else returns normalization data
     */
    double normalize(double value, double min, double max,int a, int b) {
        if(value==max) return b;
        if(value==min) return a;
        return (b-a)*((value-min)/(max-min))+a;
    }
}
