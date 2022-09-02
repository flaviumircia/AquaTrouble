package com.flaviumircia.aquatrouble.misc;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.database.ActivityChecker;
import com.flaviumircia.aquatrouble.database.DaoClass;
import com.flaviumircia.aquatrouble.database.Database;
import com.flaviumircia.aquatrouble.database.DbExists;
import com.flaviumircia.aquatrouble.database.NotificationsModel;
import com.flaviumircia.aquatrouble.menufragments.CurrentDamage;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.flaviumircia.aquatrouble.restdata.retrofit.SectorDataSearchApi;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NotificationService extends Service {

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
                        String regex="^(08):(20):[0-9]{2}$|^(11):(20):[0-9]{2}$|^(15):(20):[0-9]{2}$|^(22):(20):[0-9]{2}$|^(10):(17):[0-9]{2}$";
                        Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
                        Matcher matcher=pattern.matcher(currentTime.getCurrent_time());
                        Log.d(TAG, "run: "+currentTime.getCurrent_time());
                        if(matcher.find())
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
                if(x.getAddress().equals(y.getAddress())){
                    if(!x.getDate_time().equals(y.getExpected_date()))
                    {   String title="The expected date has changed";
                        String content="The date changed from: "+x.getDate_time()+", to: "+y.getExpected_date();
                        pushNotif(title,content);
                        database.getDao().updateDateTime(y.getExpected_date(),x.getAddress());
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

    private void pushNotif(String title,String content) {
        Intent intent = new Intent(getApplicationContext(), CurrentDamage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_piggy_bank)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent).setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1,builder.build());
    }
}