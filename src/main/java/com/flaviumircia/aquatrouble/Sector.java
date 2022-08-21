package com.flaviumircia.aquatrouble;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector);
        //instantiate views
        TextView textView=findViewById(R.id.textView);
        ImageView icon=findViewById(R.id.sectorIcon);

        //get the parameters passed
        String sector=sectorTitle();
        int icon_id=sectorIcon();

        //set the views
        icon.setImageResource(icon_id);
        textView.setText(sector);
    }

    private String sectorTitle()
    {
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            return extras.getString("sector");
        }
        return "Sector not found";
    }

    private int sectorIcon() {
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            return extras.getInt("icon");
        }
        return -100;
    }


}