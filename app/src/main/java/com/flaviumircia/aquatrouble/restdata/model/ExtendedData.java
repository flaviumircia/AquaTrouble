package com.flaviumircia.aquatrouble.restdata.model;

public class ExtendedData{
    private String remaining_days;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getRemaining_days() {
        return remaining_days;
    }

    public void setRemaining_days(String remaining_days) {
        this.remaining_days = remaining_days;
    }
}
