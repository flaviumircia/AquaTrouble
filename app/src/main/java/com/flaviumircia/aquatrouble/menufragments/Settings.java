package com.flaviumircia.aquatrouble.menufragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
import com.flaviumircia.aquatrouble.settings_pref_activities.About;
import com.flaviumircia.aquatrouble.settings_pref_activities.BugSpotting;
import com.flaviumircia.aquatrouble.settings_pref_activities.FaqActivity;
import com.flaviumircia.aquatrouble.settings_pref_activities.FeedbackProvider;
import com.flaviumircia.aquatrouble.settings_pref_activities.MakeDonation;

public class Settings extends PreferenceFragmentCompat {
    private static final String TAG = "Settings";
    private final String file="LANGUAGE_PREF";
    private final String notif_pref="NOTIFICATION_PREF";
    private ListPreference theme_switching;
    private ListPreference notification_status;
    private ListPreference language_pref;
    private Preference bug_spotting;
    private Preference feedback;
    private Preference tos;
    private Preference about;
    private Preference support;
    private Preference faq;
    private LanguageSetter languageSetter;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(requireContext(),file);
        preferenceLanguageSetter.setTheLanguage();
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onStop() {
        super.onStop();

    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        theme_switching=(ListPreference) getPreferenceManager().findPreference("change_theme");
        bug_spotting=getPreferenceScreen().findPreference("bug_spotting");
        support=getPreferenceScreen().findPreference("app_donation");
        feedback=getPreferenceScreen().findPreference("suggestion");
        tos=getPreferenceScreen().findPreference("tos");
        about=getPreferenceScreen().findPreference("about");
        notification_status=(ListPreference)getPreferenceManager().findPreference("notifications");
        language_pref=(ListPreference) getPreferenceManager().findPreference("language_settings");
        faq=getPreferenceScreen().findPreference("faq");
        setTheLanguage(language_pref);
        areNotifsOn(notification_status);
        theme_switch(theme_switching);
        onClickMethods(bug_spotting,feedback,tos,about,support);

        return super.onCreateView(inflater, container, savedInstanceState);

    }



    private void setTheLanguage(ListPreference language) {
        SharedPreferences sharedPreferences= requireActivity().getSharedPreferences(file,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        language.setOnPreferenceChangeListener((preference, newValue) -> {
            editor.putString("lang",newValue.toString());
            editor.apply();
            startActivity(new Intent(getActivity(), MainMap.class));
            requireActivity().finish();
            return true;
        });

    }

    private void areNotifsOn(ListPreference notification_status) {
        SharedPreferences sharedPreferences=requireActivity().getSharedPreferences(notif_pref,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        notification_status.setOnPreferenceChangeListener((preference, newValue) -> {
                editor.putString("notif_status",newValue.toString());
                editor.apply();
                return true;
        });
    }

    private void onClickMethods(Preference bug_spotting, Preference feedback,Preference tos,Preference about,Preference support) {
        faq.setOnPreferenceClickListener(preference -> {startActivity(new Intent(requireActivity(), FaqActivity.class));return true;});

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
        support.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), MakeDonation.class));
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

            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(requireActivity());
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("mode",AppCompatDelegate.getDefaultNightMode());
            editor.apply();
            return true;
        });
    }

}