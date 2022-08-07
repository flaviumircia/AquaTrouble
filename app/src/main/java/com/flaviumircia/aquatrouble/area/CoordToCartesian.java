package com.flaviumircia.aquatrouble.area;

public class CoordToCartesian {

    private double x,y;
    private double lat,lng;
    private final int radius=6378100;

    /**
     * Default constructor
     */
    protected CoordToCartesian(){}

    /**
     * Sets the coordinates given as parameters to Class fields
     * @param north
     * @param east
     */
    protected CoordToCartesian(double north, double east)
    {
        this.lng=east;
        this.lat=north;
    }

    /**
     * The method transforms the Polar Coordinates to Cartesian Coordinates
     * using these formulas
     */
    protected void polarToCartesian()
    {
        this.y=radius*Math.cos(lat)*Math.cos(lng);
        this.x=radius*Math.cos(lat)*Math.sin(lng);
    }

    /**
     * X getter
     * @return x value
     */
    protected double getX() {
        return x;
    }

    /**
     * Y getter
     * @return y value
     */
    protected double getY() {
        return y;
    }
}
