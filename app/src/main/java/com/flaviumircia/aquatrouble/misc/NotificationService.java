package com.flaviumircia.aquatrouble.misc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.flaviumircia.aquatrouble.MainMap;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.StreetDetails;
import com.flaviumircia.aquatrouble.database.ActivityChecker;
import com.flaviumircia.aquatrouble.database.DaoClass;
import com.flaviumircia.aquatrouble.database.Database;
import com.flaviumircia.aquatrouble.database.DbExists;
import com.flaviumircia.aquatrouble.database.NotificationsModel;
import com.flaviumircia.aquatrouble.menufragments.CurrentDamage;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.model.ExtendedData;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.flaviumircia.aquatrouble.restdata.retrofit.SectorDataSearchApi;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NotificationService extends Service {
    private final String notif_pref="NOTIFICATION_PREF";
    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 60;
    CompositeDisposable compositeDisposable;
    Database database;
    DaoClass daoClass;
    Retrofit retrofit;
    SectorDataSearchApi sectorDataSearchApi;
    List<NotificationsModel> modelList;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
//        stoptimertask();
        super.onDestroy();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000L); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        CurrentTime currentTime=new CurrentTime();
                        String regex="^(08):(20):[0-9]{2}$|^(11):(20):[0-9]{2}$|^(15):(20):[0-9]{2}$|^(22):(20):[0-9]{2}$|^(23):(36):[0-9]{2}$";
                        Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
                        Matcher matcher=pattern.matcher(currentTime.getCurrent_time());
                        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(notif_pref,MODE_PRIVATE);
                        String notif_status=sharedPreferences.getString("notif_status","notif_on");
                        String engagement_regex="^(09):(00):[0-9]{2}$";
                        String engagement_regex_2="^(17):(00):[0-9]{2}$|^(20):(00):[0-9]{2}$|^(13):(00):[0-9]{2}$|^(19):(21):[0-9]{2}$";
                        Pattern engagement_pattern=Pattern.compile(engagement_regex,Pattern.CASE_INSENSITIVE);
                        Matcher engagement_matcher=engagement_pattern.matcher(currentTime.getCurrent_time());
                        Pattern engagement_pattern_2=Pattern.compile(engagement_regex_2,Pattern.CASE_INSENSITIVE);
                        Matcher engagement_matcher_2=engagement_pattern_2.matcher(currentTime.getCurrent_time());
                        String title_1=getString(R.string.new_day_new_damage);
                        String content_1=getString(R.string.see_on_what_street);
                        String title_2=getString(R.string.throw_an_eye);
                        String content_2=getString(R.string.maybe_someting_changed);
                        if(notif_status.equals("notif_on")){
                            if(engagement_matcher.find())
                                engagementNotifications(title_1,content_1);
                            if(engagement_matcher_2.find())
                                engagementNotifications(title_2,content_2);
                        }
                        if(matcher.find() && notif_status.equals("notif_on"))
                        {

                            if(isDatabase())
                            {
                                compositeDisposable=new CompositeDisposable();
                                database= Database.getDatabase(getApplicationContext().getApplicationContext());
                                daoClass=database.getDao();
                                fetchData();
                            }
                        }
                    }

                });
            }
        };
    }

    private void fetchData() {
        retrofit= RetrofitClient.getInstance();
        sectorDataSearchApi=retrofit.create(SectorDataSearchApi.class);
        compositeDisposable.add(daoClass.getAllNotifData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((data)->getTheData(data)));
        compositeDisposable.add(sectorDataSearchApi.getData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data -> getTheApiData(data)));
    }

    private void getTheApiData(List<Data> data) {
        for(NotificationsModel x:this.modelList){
            for(Data y:data)
            {
                if(x.getAddress().equals(y.getAddress()) && x.getStreet_no().equals(y.getNumar())){
                    if(!x.getDate_time().equals(y.getExpected_date()))
                    {   String title="The expected date has changed";
                        String content=x.getAddress()+" changed from: "+x.getDate_time()+", to: "+y.getExpected_date();

                        ExtendedData extendedData=new ExtendedData();
                        extendedData.setData(y);
                        CurrentDate currentDate=new CurrentDate();

                        DateDiff dateDiff=new DateDiff(extendedData.getData().getExpected_date(), currentDate.getCurrent_date());

                        long diff=dateDiff.makeDifference();

                        if(diff>=0){
                            String days_until_finished=String.valueOf(diff/1000/60/60/24);
                            pushNotif(title,content,y.getAddress(),y.getAffected_agent(),y.getExpected_date(),days_until_finished,y.getNumar(),y.getSector());
                            database.getDao().updateDateTime(y.getExpected_date(),x.getAddress());
                        }
                    }
                }
            }
        }
    }

    private void getTheData(List<NotificationsModel> data) {
        this.modelList=data;
    }


    private Boolean isDatabase() {
        DbExists dbExists=new DbExists();
        ActivityChecker activityChecker= new ActivityChecker();
        if(dbExists.doesDatabaseExists(getApplicationContext().getApplicationContext(),"DATABASE") && activityChecker.checkNotifDatabase(getApplicationContext().getApplicationContext())!=null)
        {
            return true;
        }
        return false;
    }

    private void engagementNotifications(String title,String content){
        Intent intent=new Intent(getApplicationContext(), MainMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final int flag =  Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),(int) System.currentTimeMillis(),intent,flag);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{200,60,200,60,200})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify((int) System.currentTimeMillis() ,builder.build());
    }

    private void pushNotif(String title,String content,String address,String affected_agent,String expected_date,String days_counter,String numbers,String sector) {
        Intent intent = new Intent(getApplicationContext(), StreetDetails.class);
        int resource_id=0;
        intent.putExtra("sector",sector);
        intent.putExtra("street_title",address);
        intent.putExtra("street_number",numbers);
        intent.putExtra("expected_date",expected_date);
        intent.putExtra("affected_agent",affected_agent);
        intent.putExtra("remaining_days",days_counter);
        intent.putExtra("lat","0");
        intent.putExtra("lng","0");
        intent.putExtra("from_notif",true);
        resource_id=getResourceId(sector);
        intent.putExtra("icon_id",resource_id);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final int flag =  Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),(int) System.currentTimeMillis(),intent,flag);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{100,60,200})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);;
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify((int) System.currentTimeMillis(),builder.build());
    }
    private int getResourceId(String sector) {
        switch (sector)
        {
            case "1":
                return R.drawable.ic_arc_triumf;
            case "2":
                return R.drawable.ic_roata_mare;
            case "3":
                return R.drawable.ic_parc_ior;
            case "4":
                return R.drawable.ic_mausoleu;
            case "5":
                return R.drawable.ic_palat_parlament;
            case "6":
                return R.drawable.ic_lacul_morii;
        }
        return R.drawable.ic_arc_triumf;
    }

}