package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageSetter {
    public void setLocale(String language, Context context) {


        //Initialize resources
        Resources resource= context.getResources();
        //Display metrics
        DisplayMetrics metrics=resource.getDisplayMetrics();

        //Initialize config

        Configuration configuration= resource.getConfiguration();

        //Initialize locale
        configuration.locale=new Locale(language);
        resource.updateConfiguration(configuration,metrics);

    }
}
