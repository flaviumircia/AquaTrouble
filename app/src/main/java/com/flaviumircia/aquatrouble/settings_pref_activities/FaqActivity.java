package com.flaviumircia.aquatrouble.settings_pref_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.adapter.faq.FaqAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        data.add(new FaqModel(getString(R.string.sync_title),getString(R.string.content)));
        data.add(new FaqModel(getString(R.string.sync_title),getString(R.string.content)));
        data.add(new FaqModel(getString(R.string.sync_title),getString(R.string.content)));
        data.add(new FaqModel(getString(R.string.sync_title),getString(R.string.content)));
        return data;

    }

    private void displayData(RecyclerView recyclerView) {
        faqAdapter=new FaqAdapter(this,data);
        recyclerView.setAdapter(this.faqAdapter);
    }
}