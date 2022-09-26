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
    @NonNull
    private String date_time;

    @ColumnInfo(name = "street_no")
    @Nullable
    private String street_no;

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

    @NonNull
    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(@NonNull String date_time) {
        this.date_time = date_time;
    }

    @Nullable
    public String getStreet_no() {
        return street_no;
    }

    public void setStreet_no(@Nullable String street_no) {
        this.street_no = street_no;
    }
}
