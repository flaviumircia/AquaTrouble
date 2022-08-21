package com.flaviumircia.aquatrouble.map.settings;

import org.osmdroid.util.GeoPoint;

public interface MapPointCorrecter {
    public GeoPoint correctPolygonCenter(String polygon_title,GeoPoint default_geoPoint);
}
