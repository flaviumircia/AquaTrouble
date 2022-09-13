package com.flaviumircia.aquatrouble.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.Eula;
import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.MainMap;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.database.Database;
import com.flaviumircia.aquatrouble.database.UserModel;

public class SecondFragment extends Fragment {
    private final String file="LANGUAGE_PREF";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);

        LanguageSetter languageSetter=new LanguageSetter();
        //set the language
        SharedPreferences sharedPreferences= getContext().getSharedPreferences(file, Context.MODE_PRIVATE);
        String language=sharedPreferences.getString("lang",null);
        languageSetter.setLocale(language,getContext());
        ImageButton continueButton = v.findViewById(R.id.continueButton);
        Animation ranim = (Animation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

        continueButton.setAnimation(ranim);

        CheckBox checkBox = v.findViewById(R.id.tosCheckBox);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                view.cancelPendingInputEvents();
                startActivity(new Intent(getActivity(),Eula.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#0BC1E1"));
            }
        };
        SpannableString linkText;
        linkText=new SpannableString(getActivity().getString(R.string.tosText));
        linkText.setSpan(clickableSpan, 0, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence cs = TextUtils.expandTemplate(
                "Accept ^1 ", linkText);

        checkBox.setText(cs);
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());

        checkIfAccepted(checkBox, continueButton);

        return v;
    }

    private void checkIfAccepted(CheckBox checkBox, ImageButton continueButton) {
        Animation scaleUp= AnimationUtils.loadAnimation(getActivity(),R.anim.scale_up);

        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                continueButton.setOnClickListener(view -> {
                    // if the checkbox is checked and the continue button is pushed

                    startActivity(new Intent(getActivity(), MainMap.class)); // start new activity
                    saveStatus(); // save the check button status
                    getActivity().finish();
                });
            }
        });

        if (!checkBox.isChecked()) {
            // if the checkbox wasn't checked
            continueButton.setOnClickListener(view ->{ // click on the button and show toast message
                continueButton.startAnimation(scaleUp);

                Toast.makeText(getActivity(),
                            "Pentru a putea continua trebuie sa acceptati termenii si conditiile",
                            Toast.LENGTH_SHORT).show();});
        }
    }

    //save status method
    private void saveStatus() {   //always true because it is called inside the isChecked method
        boolean hasAccepted = true;

        UserModel userModel = new UserModel();

        userModel.setHasAccepted(hasAccepted);//setting status for object

        Database.getDatabase(getActivity().getApplicationContext()).getDao().insertAllData(userModel); // pushing the object to the database

        Toast.makeText(getActivity(), "Ai acceptat termenii si conditiile noastre!", Toast.LENGTH_SHORT).show(); // showing toast
    }
}
