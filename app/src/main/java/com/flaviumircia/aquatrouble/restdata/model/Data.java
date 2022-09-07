package com.flaviumircia.aquatrouble.restdata.model;

public class Data{
    private int id;
    private String sector,address,neighborhood, numar;
    private String concatanated_numbers;
    private String expected_date,today_date;
    private String affected_agent;
    private int count;
    private double latitude;

    private double longitude;
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

    public Data(int id, String sector, String address, String neighborhood, String numar,String concatanated_numbers, String affected_agent, String expected_date, String today_date, double latitude, double longitude, int frequency) {
        this.id = id;
        this.sector = sector;
        this.address = address;
        this.neighborhood = neighborhood;
        this.numar = numar;
        this.expected_date = expected_date;
        this.today_date = today_date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.count = frequency;
        this.affected_agent=affected_agent;
        this.concatanated_numbers=concatanated_numbers;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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



    public String getConcatanated_numbers() {
        return concatanated_numbers;
    }

    public void setConcatanated_numbers(String concatanated_numbers) {
        this.concatanated_numbers = concatanated_numbers;
    }
}
