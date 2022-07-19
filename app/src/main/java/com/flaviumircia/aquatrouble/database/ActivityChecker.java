package com.flaviumircia.aquatrouble.database;

import android.content.Context;

import com.flaviumircia.aquatrouble.database.Database;

public class ActivityChecker {
    //checks the status of check button from the database query
    public boolean checkDatabase(Context appContext){
            return Database.getDatabase(appContext).getDao().getAllData().get(Database.getDatabase(appContext).getDao().getAllData().size()-1).isHasAccepted();
    }

}
