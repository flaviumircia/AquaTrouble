package com.flaviumircia.aquatrouble.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoClass {


    @Insert
    void insertAllData(UserModel model);

    //select all data
    @Query("select * from user ")
    List<UserModel> getAllData();

}
