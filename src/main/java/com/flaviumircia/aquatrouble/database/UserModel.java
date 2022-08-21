package com.flaviumircia.aquatrouble.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserModel {

    //primary key
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int key;


    @ColumnInfo(name = "hasAccepted")
    private boolean hasAccepted;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isHasAccepted() {
        return hasAccepted;
    }

    public void setHasAccepted(boolean hasAccepted) {
        this.hasAccepted = hasAccepted;
    }
}
