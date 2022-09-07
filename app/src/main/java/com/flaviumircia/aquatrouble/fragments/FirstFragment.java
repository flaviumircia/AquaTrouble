package com.flaviumircia.aquatrouble.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.R;

public class FirstFragment extends Fragment {
    private final String file="LANGUAGE_PREF";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_one,container,false);
        LanguageSetter languageSetter=new LanguageSetter();
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
                    languageSetter.setLocale(language,getContext());
//                    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences(file, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("lang",language);
                    editor.apply();

                }
                else
                {
                    String language="ro-Ro";
                    languageSetter.setLocale(language,getContext());
//                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("pref",0);
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences(file, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("lang",language);
                    editor.apply();
                }
            }
        });
        return v;
    }



}
