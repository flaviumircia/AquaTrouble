package com.flaviumircia.aquatrouble;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.flaviumircia.aquatrouble.menufragments.CurrentDamage;
import com.flaviumircia.aquatrouble.menufragments.Favorites;
import com.flaviumircia.aquatrouble.menufragments.Home;
import com.flaviumircia.aquatrouble.menufragments.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMap extends AppCompatActivity {

    private int position_index;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the language
        SharedPreferences sharedPreferences=this.getSharedPreferences("pref",0);
        SharedPreferences sharedPreferences1= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String language=sharedPreferences.getString("lang",null);

        LanguageSetter languageSetter=new LanguageSetter();
        languageSetter.setLocale(language,this);

        setContentView(R.layout.activity_map);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        if(savedInstanceState!=null)
        {
            position_index=savedInstanceState.getInt("position");
            bottomNavigationView.setSelectedItemId(position_index);
            if(position_index==R.id.home_icon)
                replaceFragment(new Home());
            else if(position_index==R.id.settings_icon)
                replaceFragment(new Settings());
            else if(position_index==R.id.damage_icon)
                replaceFragment(new CurrentDamage());
            else
                replaceFragment(new Favorites());
        }
        else
        {
            bottomNavigationView.setSelectedItemId(R.id.home_icon);
            replaceFragment(new Home());
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.home_icon){
                this.position_index=R.id.home_icon;
                replaceFragment(new Home());}
            else if(item.getItemId()==R.id.settings_icon){
                this.position_index=R.id.settings_icon;
                replaceFragment(new Settings());}
            else if(item.getItemId()==R.id.damage_icon){
                this.position_index=R.id.damage_icon;
                replaceFragment(new CurrentDamage());}
            else{
                this.position_index=R.id.favorites_icon;
                replaceFragment(new Favorites());}
            return true;
        });
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",position_index);
    }


}