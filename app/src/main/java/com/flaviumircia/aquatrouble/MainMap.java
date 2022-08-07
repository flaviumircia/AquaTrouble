package com.flaviumircia.aquatrouble;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flaviumircia.aquatrouble.databinding.ActivityMapBinding;
import com.flaviumircia.aquatrouble.menufragments.CurrentDamage;
import com.flaviumircia.aquatrouble.menufragments.Favorites;
import com.flaviumircia.aquatrouble.menufragments.Home;
import com.flaviumircia.aquatrouble.menufragments.Settings;

public class MainMap extends AppCompatActivity {
    //TODO: SharedPreferences for language and theme saving !!!
    //TODO: Pass data from this activity to the zoomed one with bundle (search on stackoverflow)
    //TODO: Search for a way to put Markers in the center of the polygon with null icon and title= name of the neighborhood. HINT: add empty icon in the center of the polygons with the name of them on it
    private int position_index;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the language
        SharedPreferences sharedPreferences=this.getSharedPreferences("pref",0);
        String language=sharedPreferences.getString("lang",null);
        LanguageSetter languageSetter=new LanguageSetter();
        languageSetter.setLocale(language,this);
        com.flaviumircia.aquatrouble.databinding.ActivityMapBinding binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(savedInstanceState!=null)
        {
            position_index=savedInstanceState.getInt("position");
            binding.bottomNavigationView.setSelectedItemId(position_index);
            switch (position_index){
                case R.id.home_icon:
                    replaceFragment(new Home());
                    break;
                case R.id.settings_icon:
                    replaceFragment(new Settings());
                    break;
                case R.id.damage_icon:
                    replaceFragment(new CurrentDamage());
                    break;
                case R.id.favorites_icon:
                    replaceFragment(new Favorites());
                    break;
            }
        }
        else
        {
            binding.bottomNavigationView.setSelectedItemId(R.id.home_icon);
            replaceFragment(new Home());
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home_icon:
                    this.position_index=R.id.home_icon;
                    replaceFragment(new Home());
                    break;
                case R.id.settings_icon:
                    this.position_index=R.id.settings_icon;
                    replaceFragment(new Settings());
                    break;
                case R.id.damage_icon:
                    this.position_index=R.id.damage_icon;
                    replaceFragment(new CurrentDamage());
                    break;
                case R.id.favorites_icon:
                    this.position_index=R.id.favorites_icon;
                    replaceFragment(new Favorites());
                    break;
            }
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