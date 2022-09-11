package com.flaviumircia.aquatrouble.settings_pref_activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
import com.flaviumircia.aquatrouble.restdata.model.FeedbackModel;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.flaviumircia.aquatrouble.restdata.retrofit.SpotABug;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BugSpotting extends AppCompatActivity {
    private EditText email_address;
    private EditText phone_model;
    private EditText subject;
    private EditText content;
    private Button sendTheReport;
    private Retrofit retrofitClient;
    private SpotABug spotABugApi;
    private ImageButton back_arrow;
    private String email,phone,subject_string,content_string;
    private CompositeDisposable compositeDisposable;
    private final String file ="LANGUAGE_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(this,file);
        preferenceLanguageSetter.setTheLanguage();
        setContentView(R.layout.activity_bug_spotting);
        //EditText views
        email_address=findViewById(R.id.email_addres_edittext);
        phone_model=findViewById(R.id.phone_model_edittext);
        subject=findViewById(R.id.subject_edittext);
        content=findViewById(R.id.content_edittext);
        sendTheReport=findViewById(R.id.sendTheRepButton);
        back_arrow=findViewById(R.id.back_button_fbug);
        back_arrow.setOnClickListener(view->finish());
        //api instantiation
        retrofitClient= RetrofitClient.getInstance();
        spotABugApi=retrofitClient.create(SpotABug.class);
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
            if(phone_model.getText().toString().trim().equalsIgnoreCase(""))
            {
                phone_model.setError(getString(R.string.phone_model_error));
                return;

            }else
                phone=phone_model.getText().toString();
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
            puttingData(email,phone,subject_string,content_string);


        });

    }

    private void puttingData(String email, String phone, String subject_string, String content_string) {
        compositeDisposable.add(spotABugApi.postBug(phone,subject_string,content_string,email)
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