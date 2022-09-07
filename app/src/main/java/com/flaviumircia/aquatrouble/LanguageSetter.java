package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageSetter {
    private String language;
    public void setLocale(String language, Context context) {
        this.language=language;
        //Initialize resources
        Resources resource= context.getResources();
        //Display metrics
        DisplayMetrics metrics=resource.getDisplayMetrics();

        //Initialize config
        Configuration configuration= resource.getConfiguration();
        if(language==null)
            language="eng";
        configuration.setLocale(new Locale(language.toLowerCase(Locale.ROOT)));
        resource.updateConfiguration(configuration,metrics);
    }

    public String getLanguage() {
        return language;
    }
}
