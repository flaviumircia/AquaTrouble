package com.flaviumircia.aquatrouble;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int mode=sharedPreferences.getInt("mode",-1);
        AppCompatDelegate.setDefaultNightMode(mode);
        setTheme(R.style.SplashTheme);
        startActivity(new Intent(Splash.this,MainActivity.class));
        finish();
    }

}