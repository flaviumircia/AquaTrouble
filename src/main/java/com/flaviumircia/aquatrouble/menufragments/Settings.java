package com.flaviumircia.aquatrouble.menufragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.flaviumircia.aquatrouble.R;

public class Settings extends PreferenceFragmentCompat {
    private ListPreference theme_switching;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        theme_switching=(ListPreference) getPreferenceManager().findPreference("change_theme");
        theme_switching.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if(newValue.toString().equals("dark")){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }

                else if(newValue.toString().equals("light"))
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor=preference.getSharedPreferences().edit();
                editor.putInt("mode",AppCompatDelegate.getDefaultNightMode());
                editor.apply();
                return true;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}