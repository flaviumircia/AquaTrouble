package com.flaviumircia.aquatrouble.settings_pref_activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.R;

public class About extends AppCompatActivity {
    private TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        version=findViewById(R.id.version);
        version.setText(getString(R.string.Version)+" 1.0.0");

    }
}