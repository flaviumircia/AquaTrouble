package com.flaviumircia.aquatrouble.restdata.model;

public class Data {
    private int id;
    private String sector,address,neighborhood,number;
    private String expected_date,today_date;
    private double lat,lng;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    private int frequency;

    public Data(){

    }

    public String getNumber() {
        return number;
    }

    public Data(int id, String sector, String address, String neighborhood, String number, String expected_date, String today_date, double lat, double lng, int frequency) {
        this.id = id;
        this.sector = sector;
        this.address = address;
        this.neighborhood = neighborhood;
        this.number = number;
        this.expected_date = expected_date;
        this.today_date = today_date;
        this.lat = lat;
        this.lng = lng;
        this.frequency = frequency;
    }

    public void setNumber(String number) {
        this.number = number;
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
