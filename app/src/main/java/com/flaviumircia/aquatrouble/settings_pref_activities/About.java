package com.flaviumircia.aquatrouble.settings_pref_activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.BuildConfig;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;

public class About extends AppCompatActivity {
    private TextView version;
    private final String file ="LANGUAGE_PREF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(this,file);
        preferenceLanguageSetter.setTheLanguage();
        setContentView(R.layout.activity_about);
        version=findViewById(R.id.version);
        String buildVersion= BuildConfig.VERSION_NAME;
        version.setText(getString(R.string.Version)+" "+buildVersion);

    }
}