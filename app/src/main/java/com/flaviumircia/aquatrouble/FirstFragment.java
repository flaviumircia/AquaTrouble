package com.flaviumircia.aquatrouble;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class FirstFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_one,container,false);
        RadioGroup radioGroup=v.findViewById(R.id.radioGroup);
        RadioButton checkedRadioButton=(RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton= (RadioButton) radioGroup.findViewById(i);
                boolean isChecked=checkedRadioButton.isChecked();
                if(isChecked && checkedRadioButton.getText().equals("English"))
                {
                    String language = "en";
                    setLocale(language);
                }
                else
                {
                    String language="ro-Ro";
                    setLocale(language);
                }
            }
        });
        return v;
    }

    private void setLocale(String language) {
        //Initialize resources
        Resources resource= getResources();
        //Display metrics
        DisplayMetrics metrics=resource.getDisplayMetrics();

        //Initialize config

        Configuration configuration= resource.getConfiguration();

        //Initialize locale
        configuration.locale=new Locale(language);
        resource.updateConfiguration(configuration,metrics);

    }

}
