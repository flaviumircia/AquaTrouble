package com.flaviumircia.aquatrouble.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {UserModel.class,NotificationsModel.class},version = 3,autoMigrations = {@AutoMigration(from = 2,to=3)})
public abstract class Database extends RoomDatabase {
    public abstract DaoClass getDao();
    private static Database instance;

    public static Database getDatabase(final Context context)
    {
        if(instance==null)
        {
            synchronized (Database.class)
            {
                instance= Room.databaseBuilder(context,Database.class,"DATABASE").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            }
        }
    return instance;
    }

}
