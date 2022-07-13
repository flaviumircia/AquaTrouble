package com.flaviumircia.aquatrouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainMap extends AppCompatActivity {

        SearchView searchView;
        ListView listView;
        ArrayList<String> list;
        ArrayAdapter<String> adapter;
        MenuInflater menuInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        searchView=findViewById(R.id.searchView);
        listView=findViewById(R.id.lv_listView);

        list=new ArrayList<>();
        list.add("Militari");
        list.add("Cotroceni");
        list.add("Crangasi");
        list.add("Giulesti");
        list.add("Giulesti-Sarbi");

        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(list.contains(s)){
                    adapter.getFilter().filter(s);
                }
                else
                {
                    Toast.makeText(MainMap.this, "No match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

}