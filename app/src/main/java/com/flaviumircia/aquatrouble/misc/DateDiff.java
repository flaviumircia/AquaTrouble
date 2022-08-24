package com.flaviumircia.aquatrouble.misc;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDiff {
    private String date1,date2;

    public DateDiff(String expected_date, String current_date) {
        this.date1 = expected_date;
        this.date2 = current_date;
    }

    public long makeDifference(){
        @SuppressLint("SimpleDateFormat") DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date first_date= null;
        try {
            first_date = df.parse(this.date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date second_date= null;
        try {
            second_date = df.parse(this.date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return first_date.getTime()-second_date.getTime();
    }
}
