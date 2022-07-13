package com.flaviumircia.aquatrouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.day_theme);
        startActivity(new Intent(Splash.this,MainActivity.class));
        finish();
    }

}