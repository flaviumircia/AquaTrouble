package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.adapter.damage_data.PostAdapter;
import com.flaviumircia.aquatrouble.adapter.search_data.SearchDataAdapter;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.retrofit.DamageDataApi;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.flaviumircia.aquatrouble.restdata.retrofit.SectorDataSearchApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Search extends AppCompatActivity {
    private ImageButton arrow_back;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable;
    private SectorDataSearchApi sectorDataApi;
    private DamageDataApi damageDataApi;
    private Retrofit retrofit;
    private final String file="LANGUAGE_PREF";

    private void setLanguage() {
        LanguageSetter languageSetter=new LanguageSetter();
        //set the language
        SharedPreferences sharedPreferences= this.getSharedPreferences(file, Context.MODE_PRIVATE);
        String language=sharedPreferences.getString("lang",null);
        languageSetter.setLocale(language,this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_search);
        arrow_back=findViewById(R.id.back_button_search);
        searchView=findViewById(R.id.searchView);
        compositeDisposable=new CompositeDisposable();
        recyclerView=findViewById(R.id.recyclerView_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofit= RetrofitClient.getInstance();
        sectorDataApi=retrofit.create(SectorDataSearchApi.class);
        damageDataApi=retrofit.create(DamageDataApi.class);
        arrow_back.setOnClickListener(view-> finish());

        String neighborhood=getNeighborhood();
        fetchData(neighborhood);


    }
    public String getNeighborhood(){
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            return extras.getString("neighborhood");
        }
        return "";
    }
    private void fetchData(String neighborhood) {
        if(neighborhood.equals(""))
        compositeDisposable.add(sectorDataApi.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data->displayData(data)));
        else
            compositeDisposable.add(damageDataApi.getData(neighborhood)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data->displayNeighborhoodData(data)));
    }

    private void displayNeighborhoodData(List<Data> data) {
        PostAdapter damageDataAdapter=new PostAdapter(this,data);
        recyclerView.setAdapter(damageDataAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                damageDataAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                damageDataAdapter.filter(newText);
                return true;
            }
        });
    }

    private void displayData(List<Data> data) {
        SearchDataAdapter sectorDataAdapter=new SearchDataAdapter(this,data);
        recyclerView.setAdapter(sectorDataAdapter);
        recyclerView.setAlpha(0.0f);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView.setAlpha(1.0f);
                sectorDataAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(""))
                    recyclerView.setAlpha(0.0f);
                else
                    recyclerView.setAlpha(1.0f);
                sectorDataAdapter.filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        searchView.clearFocus();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}