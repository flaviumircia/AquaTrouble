package com.flaviumircia.aquatrouble;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Search extends AppCompatActivity {
    private ImageButton filter_button,arrow_back;
    private CoordinatorLayout coordinatorLayout;
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private Button accept_button;
    private SearchView searchView;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        filter_button=findViewById(R.id.filter_button);
        coordinatorLayout=findViewById(R.id.coordinator);
        accept_button=findViewById(R.id.accept_button);
        arrow_back=findViewById(R.id.back_button_search);
        searchView=findViewById(R.id.searchView);
        recyclerView=findViewById(R.id.recyclerView_search);
        View bottomSheet=coordinatorLayout.findViewById(R.id.bottomSheet);
        BottomSheetBehavior<View> behavior=BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(true);
        behavior.setPeekHeight(0);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        onClickFilter(filter_button,behavior);
        arrow_back.setOnClickListener(view-> finish());
        Intent intent=getIntent();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

            private void filterList(String newText) {
            }
        });
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query=intent.getStringExtra(SearchManager.QUERY);
            doTheSearch(query);
        }
    }

    private void doTheSearch(String query) {
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