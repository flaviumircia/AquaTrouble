package com.flaviumircia.aquatrouble.settings_pref_activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
import com.flaviumircia.aquatrouble.restdata.model.FeedbackModel;
import com.flaviumircia.aquatrouble.restdata.retrofit.FeedbackApi;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FeedbackProvider extends AppCompatActivity {
    private EditText email_address;
    private EditText phone_model;
    private EditText subject;
    private EditText content;
    private Button sendTheReport;
    private Retrofit retrofitClient;
    private FeedbackApi feedbackApi;
    private ImageButton back_button;
    private String email,subject_string,content_string;
    private AdView bannerView;
    private CompositeDisposable compositeDisposable;
    private final String file ="LANGUAGE_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(this,file);
        preferenceLanguageSetter.setTheLanguage();
        setContentView(R.layout.activity_feedback_provider);
        email_address=findViewById(R.id.email_addres_edittext);
        phone_model=findViewById(R.id.phone_model_edittext);
        subject=findViewById(R.id.subject_edittext);
        content=findViewById(R.id.content_edittext);
        sendTheReport=findViewById(R.id.sendTheRepButton);
        bannerView=findViewById(R.id.banner_view_map_details);
        setTheBanner();
        back_button=findViewById(R.id.back_button_feedback);
        back_button.setOnClickListener(view->finish());
        //api instantiation
        retrofitClient= RetrofitClient.getInstance();
        feedbackApi=retrofitClient.create(FeedbackApi.class);
        compositeDisposable=new CompositeDisposable();

        sendTheReport.setOnClickListener(view->{
            if(email_address.getText().toString().trim().equalsIgnoreCase(""))
            {
                email_address.setError(getString(R.string.email_address_error));
                return;
            }else if(!checkForRegex(email_address.getText().toString().trim()))
            {

                email_address.setError(getString(R.string.email_address_bad_format));
                return;

            }else
            {
                email=email_address.getText().toString().trim();
            }

            if(subject.getText().toString().trim().equalsIgnoreCase(""))
            {
                subject.setError(getString(R.string.subject_error));
                return;

            }else
                subject_string=subject.getText().toString();
            if(content.getText().toString().trim().equalsIgnoreCase(""))
            {
                content.setError(getString(R.string.content_field_error));
                return;
            }else
                content_string=content.getText().toString();
            puttingData(email,subject_string,content_string);


        });

    }

    private void setTheBanner() {
        //bannerview settings
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest=new AdRequest.Builder().build();
        bannerView.loadAd(adRequest);
    }

    private void puttingData(String email, String subject_string, String content_string) {
        compositeDisposable.add(feedbackApi.postFeedback(subject_string,content_string,email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> postTheData(data)));
    }

    private void postTheData(FeedbackModel data) {
        Toast.makeText(this,"Report sent via email:"+data.getSender(), Toast.LENGTH_SHORT).show();

    }

    private boolean checkForRegex(String email_address) {
        String regex="^[A-Za-z0-9+_.-]+@((gmail.*)|(hotmail.*)|(yahoo.*))+$";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(email_address);
        return matcher.matches();
    }

}