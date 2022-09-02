package com.flaviumircia.aquatrouble.misc;

public class CurrentTime extends CurrentDate{
    private String current_date;


    private String current_time;
    public CurrentTime(){
        this.current_date=getCurrent_date();
        this.current_time=transformDateToTime(this.current_date);
    }

    @Override
    public String getCurrent_date() {
        return super.getCurrent_date();
    }
    private static String transformDateToTime(String date){
        String[] array_of_strings=date.split(" ",2);
        return array_of_strings[1];
    }
    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

}
