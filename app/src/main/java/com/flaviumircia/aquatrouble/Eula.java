package com.flaviumircia.aquatrouble;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class Eula extends AppCompatActivity {
    private static final String path= "eula.txt";
    private ImageView backArrow;
    private List<String> mLines;
    private TextView eulaTextView;
    private String finalString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);
        backArrow=findViewById(R.id.backArrow);
        eulaTextView=findViewById(R.id.eulaText);
        eulaTextView.setMovementMethod(new ScrollingMovementMethod());
        EulaFile eulaFile= new EulaFile(getApplicationContext());
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