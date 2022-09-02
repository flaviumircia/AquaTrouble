package com.flaviumircia.aquatrouble;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.model.ExtendedData;
import com.flaviumircia.aquatrouble.theme.ThemeModeChecker;

public class StreetDetailsMap extends AppCompatActivity implements ThemeModeChecker {
    private TextView street_title,street_number,sector, frequency,remaining_days,affected_agent;
    private ImageButton back_arrow;
    private ImageView icon;
    private Button show_map;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_street_details_map);
        street_title=findViewById(R.id.streetTitleDetails);
        street_number=findViewById(R.id.numbersStreetDetails);
        sector=findViewById(R.id.sectorTitle);
        frequency =findViewById(R.id.frequencyText);
        remaining_days=findViewById(R.id.daysCounter);
        back_arrow=findViewById(R.id.arrow_back_button);
        affected_agent=findViewById(R.id.affectedAgentDetails);
        icon=findViewById(R.id.sectorIconDetails);
        int icon_res=getResourceIcon();
        icon.setImageResource(icon_res);

        Window window=getWindow();
        int nightModeFlags=getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        setCustomTheme(window,nightModeFlags);
        ExtendedData model=getFields();

        onClick(back_arrow,model);

        sector.setText("Sector " + model.getData().getSector());
        street_title.setText(model.getData().getAddress());
        street_number.setText(getString(R.string.street_numbers)+": "+model.getData().getConcatanated_numbers());
        frequency.setText(getString(R.string.frequency)+": " + model.getData().getCount() +" " +getString(R.string.times));
    }

    private void onClick(ImageButton back_arrow,ExtendedData model) {
        back_arrow.setOnClickListener(view->{
            finish();
        });

    }
    private int getResourceIcon(){
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            return extras.getInt("icon_id");
        }
        return -100;
    }
    private com.flaviumircia.aquatrouble.restdata.model.ExtendedData getFields()
    {   com.flaviumircia.aquatrouble.restdata.model.ExtendedData data_model=new com.flaviumircia.aquatrouble.restdata.model.ExtendedData();
        Data temp_model=new Data();
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            temp_model.setAddress(extras.getString("street_title"));
            temp_model.setCount(extras.getInt("frequency"));
            temp_model.setConcatanated_numbers(extras.getString("street_number"));
            temp_model.setSector(extras.getString("sector"));
            temp_model.setAffected_agent(extras.getString("affected_agent"));
            temp_model.setLat(extras.getDouble("lat"));
            temp_model.setLng(extras.getDouble("lng"));
            data_model.setData(temp_model);
        }
        return data_model;
    }

    @Override
    public void setCustomTheme(Window window, int system_mode) {
//            switch (system_mode){
//                case android.content.res.Configuration.UI_MODE_NIGHT_YES:
//                    icon.setColorFilter(ContextCompat.getColor(this,R.color.background_white), PorterDuff.Mode.SRC_IN);
//
//                case android.content.res.Configuration.UI_MODE_NIGHT_NO:
//                    icon.setColorFilter(ContextCompat.getColor(this,R.color.black), PorterDuff.Mode.SRC_IN);
//
//                case android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED:
//                    icon.setColorFilter(ContextCompat.getColor(this,R.color.black), PorterDuff.Mode.SRC_IN);
//
//
//
//            }
    }
}