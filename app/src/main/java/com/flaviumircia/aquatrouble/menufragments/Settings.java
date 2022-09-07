package com.flaviumircia.aquatrouble.menufragments;

import android.content.Context;
import android.content.Intent;
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

import com.flaviumircia.aquatrouble.Eula;
import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.MainMap;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.settings_pref_activities.About;
import com.flaviumircia.aquatrouble.settings_pref_activities.BugSpotting;
import com.flaviumircia.aquatrouble.settings_pref_activities.FeedbackProvider;

public class Settings extends PreferenceFragmentCompat {
    private final String file="LANGUAGE_PREF";
    private final String notif_pref="NOTIFICATION_PREF";
    private ListPreference theme_switching;
    private ListPreference notification_status;
    private ListPreference language;
    private Preference bug_spotting;
    private Preference feedback;
    private Preference tos;
    private Preference about;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        LanguageSetter languageSetter=new LanguageSetter();
        //set the language
        SharedPreferences sharedPreferences= getContext().getSharedPreferences(file, Context.MODE_PRIVATE);
        String language=sharedPreferences.getString("lang",null);
        languageSetter.setLocale(language,getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        theme_switching=(ListPreference) getPreferenceManager().findPreference("change_theme");
        bug_spotting=getPreferenceScreen().findPreference("bug_spotting");
        feedback=getPreferenceScreen().findPreference("suggestion");
        tos=getPreferenceScreen().findPreference("tos");
        about=getPreferenceScreen().findPreference("about");
        notification_status=getPreferenceScreen().findPreference("notifications");
        language=getPreferenceScreen().findPreference("language");
        setTheLanguage(language);
        areNotifsOn(notification_status);
        theme_switch(theme_switching);
        onClickMethods(bug_spotting,feedback,tos,about);

        return super.onCreateView(inflater, container, savedInstanceState);

    }
    private void setTheLanguage(ListPreference language) {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(file,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        language.setOnPreferenceChangeListener((preference, newValue) -> {
            editor.putString("lang",newValue.toString());
            editor.apply();
            startActivity(new Intent(getActivity(), MainMap.class));
            getActivity().finish();
            return true;
        });

    }

    private void areNotifsOn(ListPreference notification_status) {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(notif_pref,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        notification_status.setOnPreferenceChangeListener((preference, newValue) -> {
                editor.putString("notif_status",newValue.toString());
                editor.apply();
                return true;
        });
    }

    private void onClickMethods(Preference bug_spotting, Preference feedback,Preference tos,Preference about) {

        bug_spotting.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), BugSpotting.class));
            return true;
        });
        feedback.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), FeedbackProvider.class));
            return true;
        });
        tos.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), Eula.class));
            return true;
        });
        about.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), About.class));
            return true;
        });
    }


    private void theme_switch(ListPreference theme_switching) {
        theme_switching.setOnPreferenceChangeListener((preference, newValue) -> {
            if(newValue.toString().equals("dark"))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else if(newValue.toString().equals("light"))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor=preference.getSharedPreferences().edit();
            editor.putInt("mode",AppCompatDelegate.getDefaultNightMode());
            editor.apply();
            return true;
        });
    }

}