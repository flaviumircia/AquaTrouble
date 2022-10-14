package com.flaviumircia.aquatrouble.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.flaviumircia.aquatrouble.restdata.model.Data;

@Entity(tableName = "notifications")
public class NotificationsModel {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "address")
    @NonNull
    private String address;

    @ColumnInfo(name = "date_time")
    private String date_time;

    @ColumnInfo(name = "street_no")
    @NonNull
    private String street_no;

    @ColumnInfo(name="neighborhood")
    @Nullable
    private String neighborhood;
    @Nullable
    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(@Nullable String neighborhood) {
        this.neighborhood = neighborhood;
    }



    @ColumnInfo(name="sector")
    @NonNull

    private String sector;

    @ColumnInfo(name="affected_agent")
    @NonNull
    private String affected_agent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    @NonNull
    public String getStreet_no() {
        return street_no;
    }

    public void setStreet_no(@NonNull String street_no) {
        this.street_no = street_no;
    }

    @NonNull
    public String getSector() {
        return sector;
    }

    public void setSector(@NonNull String sector) {
        this.sector = sector;
    }

    @NonNull
    public String getAffected_agent() {
        return affected_agent;
    }

    public void setAffected_agent(@NonNull String affected_agent) {
        this.affected_agent = affected_agent;
    }
}

