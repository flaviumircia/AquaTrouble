package com.flaviumircia.aquatrouble;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.adapter.sector_data.SectorDataAdapter;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.flaviumircia.aquatrouble.restdata.retrofit.SectorDataApi;
import com.flaviumircia.aquatrouble.theme.ThemeModeChecker;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Sector extends AppCompatActivity implements ThemeModeChecker {
    private ImageView icon;
    private ImageButton back_arrow;
    private CompositeDisposable compositeDisposable;
    private SectorDataApi sectorDataApi;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector);

        //instantiate views
        TextView textView=findViewById(R.id.textView);
        icon=findViewById(R.id.sectorIcon);
        compositeDisposable=new CompositeDisposable();
        recyclerView=findViewById(R.id.damaged_streets_sector_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofit=RetrofitClient.getInstance();
        sectorDataApi=retrofit.create(SectorDataApi.class);
        back_arrow=findViewById(R.id.backButtonSector);
        back_arrow.setOnClickListener(view -> finish());
        //get the parameters passed
        String sector=sectorTitle();
        int icon_id=sectorIcon();

        //set the views
        icon.setImageResource(icon_id);
        textView.setText(sector);

        //getting the data from the api
        int sector_number=getTheSectorNumber(sector);
        fetchData(sector_number);

    }

    private void fetchData(int sector_number) {
            compositeDisposable.add(sectorDataApi.getData(sector_number)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data->displayData(data)));
    }

    private void displayData(List<Data> data) {
        SectorDataAdapter sectorDataAdapter=new SectorDataAdapter(this,data,sectorIcon());
        recyclerView.setAdapter(sectorDataAdapter);

    }
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    private int getTheSectorNumber(String sector) {
        String[] arrary_of_sector=sector.split(" ",2);
        return Integer.parseInt(arrary_of_sector[1]);
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


    @Override
    public void setCustomTheme(Window window, int system_mode) {
        switch (system_mode) {
            case Configuration.UI_MODE_NIGHT_YES:
                window.setStatusBarColor(Color.parseColor("#2B2B2B"));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                window.setStatusBarColor(Color.parseColor("#EFE9E3"));
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                window.setStatusBarColor(Color.parseColor("#EFE9E3"));
                break;
        }
    }
}