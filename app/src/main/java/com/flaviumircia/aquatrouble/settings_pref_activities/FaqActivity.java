package com.flaviumircia.aquatrouble.settings_pref_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;

import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.adapter.faq.FaqAdapter;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
import com.flaviumircia.aquatrouble.settings_pref_activities.models.FaqModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class FaqActivity extends AppCompatActivity {
    private ImageButton back_button;
    private RecyclerView recyclerView;
    private FaqAdapter faqAdapter;
    private List<FaqModel> data;
    private final String file="LANGUAGE_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(this,file);
        preferenceLanguageSetter.setTheLanguage();

        setContentView(R.layout.activity_faq);
        //Views
        back_button=findViewById(R.id.back_button_faq);
        recyclerView=findViewById(R.id.recyclerView_faq);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Controllers
        back_button.setOnClickListener(view->finish());

        //set the model
        data=setTheData();
        //display data
        displayData(recyclerView);


    }

    private List<FaqModel> setTheData() {
        List<FaqModel> data=new ArrayList<>();
        data.add(new FaqModel(getString(R.string.what_is_aquatrouble),getString(R.string.aquatrouble_description)));
        data.add(new FaqModel(getString(R.string.how_to_add_address_to_fav),getString(R.string.add_address_to_fav)));
        data.add(new FaqModel(getString(R.string.cant_find_my_address),getString(R.string.cant_find_my_address_explanation)));
        data.add(new FaqModel(getString(R.string.when_i_should_receive_notifications),getString(R.string.when_i_should_receive_notfi_explanations)));
        data.add(new FaqModel(getString(R.string.how_to_delete_an_address),getString(R.string.how_to_delete_an_address_explanation)));
        data.add(new FaqModel(getString(R.string.what_is_acc),getString(R.string.what_is_acc_explanation)));
        data.add(new FaqModel(getString(R.string.street_address_is_partially_visible),getString(R.string.street_address_is_partially_visible_explanation)));
        data.add(new FaqModel(getString(R.string.street_numbers_are_partially_visible),getString(R.string.street_numbers_are_partially_visible_explanation)));
        data.add(new FaqModel(getString(R.string.send_us_a_feedback),getString(R.string.send_us_feedback_explanation)));
        return data;

    }

    private void displayData(RecyclerView recyclerView) {
        faqAdapter=new FaqAdapter(this,data);
        recyclerView.setAdapter(this.faqAdapter);
    }
}