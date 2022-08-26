package com.flaviumircia.aquatrouble.restdata.model;

public class Data{
    private int id;
    private String sector,address,neighborhood, numar;
    private String expected_date,today_date;
    private String affected_agent;
    private int frequency;
    private double lat,lng;

    public Data(){

    }

    public String getAffected_agent() {
        return affected_agent;
    }

    public void setAffected_agent(String affected_agent) {
        this.affected_agent = affected_agent;
    }

    public Data(String address) {
        this.address = address;
    }

    public Data(int id, String sector, String address, String neighborhood, String numar, String affected_agent, String expected_date, String today_date, double lat, double lng, int frequency) {
        this.id = id;
        this.sector = sector;
        this.address = address;
        this.neighborhood = neighborhood;
        this.numar = numar;
        this.expected_date = expected_date;
        this.today_date = today_date;
        this.lat = lat;
        this.lng = lng;
        this.frequency = frequency;
        this.affected_agent=affected_agent;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getExpected_date() {
        return expected_date;
    }

    public void setExpected_date(String expected_date) {
        this.expected_date = expected_date;
    }

    public String getToday_date() {
        return today_date;
    }

    public void setToday_date(String today_date) {
        this.today_date = today_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
