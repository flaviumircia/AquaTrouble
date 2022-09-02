package com.flaviumircia.aquatrouble.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DaoClass {
    @Insert
    void insertAllData(UserModel model);

    //select all data
    @Query("select * from user ")
    List<UserModel> getAllData();

    @Insert
    void insertNotifData(NotificationsModel model);

    @Query("DELETE FROM notifications WHERE address=:address and street_no=:street_no")
    void delete(String address,String street_no);


    @Query("UPDATE notifications SET date_time=:date_time WHERE address=:address")
    void updateDateTime(String date_time,String address);

    @Query("select id,address,date_time from notifications where address=:address")
    Maybe<NotificationsModel> getNotifData(String address);


    @Query("select id,address,date_time,street_no from notifications")
    Maybe<List<NotificationsModel>> getAllNotifData();
}
