package com.flaviumircia.aquatrouble;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {UserModel.class},version = 1)

public abstract class Database extends RoomDatabase {
    public abstract DaoClass getDao();
    private static Database instance;


    static Database getDatabase(final Context context)
    {
        if(instance==null)
        {
            synchronized (Database.class)
            {
                instance= Room.databaseBuilder(context,Database.class,"DATABASE").allowMainThreadQueries().build();
            }
        }
    return instance;
    }
}
