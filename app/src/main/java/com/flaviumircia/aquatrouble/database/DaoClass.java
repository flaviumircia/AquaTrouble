package com.flaviumircia.aquatrouble.database;

import androidx.room.Dao;
import androidx.room.Ignore;
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


    @Query("select id,sector,neighborhood,address,affected_agent,date_time,street_no from notifications where id is not null and address is not null and date_time is not null and street_no is not null and neighborhood is not null and sector is not null and affected_agent is not null")
    Maybe<List<NotificationsModel>> getAllNotifData();
}
