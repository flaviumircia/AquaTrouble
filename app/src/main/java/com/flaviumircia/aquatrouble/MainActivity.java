package com.flaviumircia.aquatrouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override

    //on create method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AquaTrouble);
        DbExists dbExists=new DbExists();
        ActivityChecker activityChecker=new ActivityChecker();
        if(dbExists.doesDatabaseExists(getApplicationContext(),"DATABASE") && activityChecker.checkDatabase(getApplicationContext()))
        {
            startActivity(new Intent(MainActivity.this,MainMap.class));
            onDestroy();
        }
        setContentView(R.layout.activity_main);
        Button continueButton = findViewById(R.id.continueButton);
        CheckBox checkBox = findViewById(R.id.tosCheckBox);
        checkIfAccepted(checkBox,continueButton);
    }
    // check if checkbox is checked
    private void checkIfAccepted(CheckBox checkBox, Button continueButton) {
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked())
            {
                continueButton.setOnClickListener(view -> { // if the checkbox is checked and the continue button is pushed
                    startActivity(new Intent(MainActivity.this,MainMap.class)); // start new activity
                    saveStatus(); // save the check button status
                    onDestroy(); // destroying the activity
                });
            }
        });

        if(!checkBox.isChecked()){ // if the checkbox wasn't checked
            continueButton.setOnClickListener(view -> // click on the button and show toast message
                    Toast.makeText(MainActivity.this,
                    "Pentru a putea continua trebuie sa acceptati termenii si conditiile",
                    Toast.LENGTH_SHORT).show());
        }
    }
    //save status method
    private void saveStatus()
    {   //always true because it is called inside the isChecked method
        boolean hasAccepted=true;

            UserModel userModel=new UserModel();

            userModel.setHasAccepted(hasAccepted);//setting status for object

            Database.getDatabase(getApplicationContext()).getDao().insertAllData(userModel); // pushing the object to the database

            Toast.makeText(this, "Ai acceptat termenii si conditiile noastre!", Toast.LENGTH_SHORT).show(); // showing toast
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}