package com.flaviumircia.aquatrouble;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;

import java.util.List;
public class Eula extends AppCompatActivity {
    private static final String path= "eula.txt";
    private static final String path_eng="eula_eng.txt";
    private final String file="LANGUAGE_PREF";
    private ImageView backArrow;
    private List<String> mLines;
    private TextView eulaTextView;
    private String finalString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(this,file);
        preferenceLanguageSetter.setTheLanguage();

        setContentView(R.layout.activity_eula);
        backArrow=findViewById(R.id.back_button_tos);
        eulaTextView=findViewById(R.id.eulaText);
        eulaTextView.setMovementMethod(new ScrollingMovementMethod());
        EulaFile eulaFile= new EulaFile(getApplicationContext());
        if(preferenceLanguageSetter.getCurrentLanguage().equals("en"))
            mLines=eulaFile.readLine(path_eng);
        else
            mLines=eulaFile.readLine(path);
        finalString="";
        for(String x:mLines)
        {
            finalString=finalString + x + "\n";
        }
        eulaTextView.setText(finalString);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}