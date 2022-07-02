package com.flaviumircia.aquatrouble;

import android.content.Context;

import java.io.File;
//checks if the sqlite database exists
public class DbExists {
    public boolean doesDatabaseExists(Context context, String dbName)
    {
        File dbFile=context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
