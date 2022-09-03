package com.flaviumircia.aquatrouble;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.database.Database;
import com.flaviumircia.aquatrouble.database.NotificationsModel;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.model.ExtendedData;
import com.flaviumircia.aquatrouble.theme.ThemeModeChecker;

public class StreetDetails extends AppCompatActivity implements ThemeModeChecker {
    private TextView street_title,street_number,sector,expected_date,remaining_days,affected_agent;
    private ImageButton back_arrow;
    private Button add_to_fav;
    private ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_street_details);
        street_title=findViewById(R.id.streetTitleDetails);
        street_number=findViewById(R.id.numbersStreetDetails);
        sector=findViewById(R.id.sectorTitle);
        expected_date=findViewById(R.id.frequencyText);
        remaining_days=findViewById(R.id.daysCounter);
        back_arrow=findViewById(R.id.arrow_back_button);
        affected_agent=findViewById(R.id.affectedAgentDetails);
        icon=findViewById(R.id.sectorIconDetails);
        add_to_fav=findViewById(R.id.addToFavButton);

        int icon_res=getResourceIcon();
        icon.setImageResource(icon_res);

        Window window=getWindow();
        int nightModeFlags=getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        setCustomTheme(window,nightModeFlags);
        ExtendedData model=getFields();

        onClick(back_arrow,add_to_fav,model);

        sector.setText("Sector " + model.getData().getSector());
        street_title.setText(model.getData().getAddress());
        affected_agent.setText(getString(R.string.affected_agent)+": "+model.getData().getAffected_agent());
        street_number.setText(getString(R.string.street_numbers)+": "+model.getData().getNumar());
        expected_date.setText(getString(R.string.expected_fixing_date)+": " + model.getData().getExpected_date());
        remaining_days.setText(getString(R.string.remaining_days_until_fix)+": " + model.getRemaining_days());

    }

    @SuppressLint("ClickableViewAccessibility")
    private void onClick(ImageButton back_arrow, Button add_to_fav, ExtendedData data_model) {
        back_arrow.setOnClickListener(view->{
            finish();
        });
        add_to_fav.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.teal_custom));
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.light_blue));

                    view.performClick();
                }else if(motionEvent.getAction()==MotionEvent.ACTION_CANCEL) {
                    view.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.light_blue));
                }
                return true;
            }
        });
        add_to_fav.setOnClickListener(view ->{
            NotificationsModel model=new NotificationsModel();
            model.setAddress(data_model.getData().getAddress());
            model.setStreet_no(data_model.getData().getNumar());
            model.setDate_time(data_model.getData().getExpected_date());
            Database.getDatabase(getApplicationContext()).getDao().insertNotifData(model);
            Toast.makeText(this, R.string.address_was_added, Toast.LENGTH_SHORT).show();
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
    private ExtendedData getFields()
    {   ExtendedData data_model=new ExtendedData();
        Data temp_model=new Data();
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            temp_model.setAddress(extras.getString("street_title"));
            temp_model.setExpected_date(extras.getString("expected_date"));
            temp_model.setNumar(extras.getString("street_number"));
            temp_model.setSector(extras.getString("sector"));
            temp_model.setAffected_agent(extras.getString("affected_agent"));
            data_model.setData(temp_model);
            data_model.setRemaining_days(extras.getString("remaining_days"));
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