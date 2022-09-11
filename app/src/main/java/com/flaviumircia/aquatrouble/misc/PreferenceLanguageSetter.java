package com.flaviumircia.aquatrouble.misc;

import android.content.Context;
import android.content.SharedPreferences;

import com.flaviumircia.aquatrouble.LanguageSetter;

public class PreferenceLanguageSetter {
    Context context;
    String file;
    LanguageSetter languageSetter;
    public PreferenceLanguageSetter(Context context,String file){
        this.context=context;
        this.file=file;
    }
    public void setTheLanguage(){
        String language=getStringSharedPreference();
        languageSetter=new LanguageSetter();
        languageSetter.setLocale(language,context);
    }
    private String getStringSharedPreference(){
        SharedPreferences sharedPreferences= context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString("lang",null);
    }
    public String getCurrentLanguage(){
        return getStringSharedPreference();
    }
}
