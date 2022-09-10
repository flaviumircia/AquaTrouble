package com.flaviumircia.aquatrouble.settings_pref_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;

import com.flaviumircia.aquatrouble.R;

public class FaqActivity extends AppCompatActivity {
    private ImageButton back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        //Views
        back_button=findViewById(R.id.back_button_faq);

        //Controllers
        back_button.setOnClickListener(view->finish());
    }
}