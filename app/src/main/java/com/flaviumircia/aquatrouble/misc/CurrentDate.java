package com.flaviumircia.aquatrouble.misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentDate {
    private String current_date;

    public CurrentDate()
    {
        SimpleDateFormat now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = Calendar.getInstance().getTime();
        this.current_date=now.format(currentTime);
    }

    public String getCurrent_date() {
        return current_date;
    }
}
