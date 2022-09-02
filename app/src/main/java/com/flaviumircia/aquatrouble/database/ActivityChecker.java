package com.flaviumircia.aquatrouble.database;

import android.content.Context;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActivityChecker {
    //checks the status of check button from the database query
    public boolean checkDatabase(Context appContext){
        if(Database.getDatabase(appContext).getDao().getAllData().size()>0)
            return Database.getDatabase(appContext).getDao().getAllData().get(Database.getDatabase(appContext).getDao().getAllData().size()-1).isHasAccepted();
        return false;
    }
    public Disposable checkNotifDatabase(Context context){

            return Database.getDatabase(context).getDao().getAllNotifData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data->displayData(data));
    }

    private String displayData(List<NotificationsModel> data) {
        if(data.size()>0)
            return data.get(data.size()-1).getAddress();
        return "";
    }
}
