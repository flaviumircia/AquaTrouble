package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.adapter.search_data.SearchDataAdapter;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.restdata.retrofit.RetrofitClient;
import com.flaviumircia.aquatrouble.restdata.retrofit.SectorDataSearchApi;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Search extends AppCompatActivity {
    private ImageButton filter_button,arrow_back;
    private CoordinatorLayout coordinatorLayout;
    private Button accept_button;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private BottomSheetBehavior<View> behavior;
    private View bottomSheet;
    private CompositeDisposable compositeDisposable;
    private SectorDataSearchApi sectorDataApi;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        filter_button=findViewById(R.id.filter_button);
        coordinatorLayout=findViewById(R.id.coordinator);
        accept_button=findViewById(R.id.accept_button);
        arrow_back=findViewById(R.id.back_button_search);
        searchView=findViewById(R.id.searchView);
        bottomSheet=coordinatorLayout.findViewById(R.id.bottomSheet);
        compositeDisposable=new CompositeDisposable();

        recyclerView=findViewById(R.id.recyclerView_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofit= RetrofitClient.getInstance();
        sectorDataApi=retrofit.create(SectorDataSearchApi.class);


        behavior=BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(true);
        behavior.setPeekHeight(0);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        searchView.requestFocus();

        onClickFilter(filter_button,behavior);

        arrow_back.setOnClickListener(view-> finish());

        fetchData();


    }

    private void fetchData() {
        compositeDisposable.add(sectorDataApi.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data->displayData(data)));
    }

    private void displayData(List<Data> data) {
        SearchDataAdapter sectorDataAdapter=new SearchDataAdapter(this,data);
        recyclerView.setAdapter(sectorDataAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sectorDataAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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

    private void onClickFilter(ImageButton filter_button, BottomSheetBehavior<View> behavior) {
        filter_button.setOnClickListener(view ->{
            closeKeyboard(view);
            behavior.setHideable(false);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });

        });
    }

    private void closeKeyboard(View current_view) {
        if(current_view!=null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(current_view.getWindowToken(), 0);
        }
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