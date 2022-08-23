package com.flaviumircia.aquatrouble.map.math;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class CalculateAreaOfPoly extends CoordToCartesian{

    private List<GeoPoint> list_of_points;
    private CoordToCartesian transformedPoints;
    private ArrayList<Double> X,Y;
    private double area;

    /**
     * Constructor of the Class
     * Instantiate X List and Y list
     * Copy the list of GeoPoints given to Class Variable
     * @param list_of_points
     */
    public CalculateAreaOfPoly(List<GeoPoint> list_of_points)
    {
        X=new ArrayList<>();
        Y=new ArrayList<>();
        this.list_of_points=list_of_points;
    }

    /**
     * Transform the Latitude and Longitude to Cartesian Coordinates
     * using the CoordToCartesian class
     * Updates the X and Y lists of the class
     */
    private void pointTransformation()
    {
        for(int i=0;i<list_of_points.size();i++)
        {
            transformedPoints=new CoordToCartesian(list_of_points.get(i).getLatitude(),list_of_points.get(i).getLongitude());
            transformedPoints.polarToCartesian();
            this.X.add(transformedPoints.getX());
            this.Y.add(transformedPoints.getY());
        }

    }

    /**
     * The method that calculates area of the polygon given x and y points of the vertex
     * @return area of the polygon
     */
    public double polyArea()
    {   area=0.0;
        pointTransformation();
        int n=list_of_points.size();
        int j=n-1;
        for(int i=0;i<n;i++)
        {
            area+=(X.get(i)+X.get(j))*(Y.get(j)-Y.get(i));

            j=i;
        }
        return Math.abs(area/2.0);
    }

}
