package com.flaviumircia.aquatrouble;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.flaviumircia.aquatrouble.database.ActivityChecker;
import com.flaviumircia.aquatrouble.database.DbExists;
import com.flaviumircia.aquatrouble.fragments.FirstFragment;
import com.flaviumircia.aquatrouble.fragments.SecondFragment;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class MainActivity extends AppCompatActivity {
    @Override

    //on create method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbExists dbExists=new DbExists();
        ActivityChecker activityChecker=new ActivityChecker();
        if(dbExists.doesDatabaseExists(getApplicationContext(),"DATABASE") && activityChecker.checkDatabase(getApplicationContext()))
        {
            startActivity(new Intent(MainActivity.this,MainMap.class));
            finish();
        }

        setContentView(R.layout.activity_main);
        ViewPager2 pager2 = findViewById(R.id.viewpager);
        FragmentStateAdapter pagerAdapter = new MyPageAdapter(this);
        WormDotsIndicator wormDotsIndicator = findViewById(R.id.worm_dots);
        pager2.setAdapter(pagerAdapter);
        wormDotsIndicator.setViewPager2(pager2);


    }

    private class MyPageAdapter extends FragmentStateAdapter {
        public MyPageAdapter(FragmentActivity fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position)
            {
                case 0: return new FirstFragment();
                case 1: return new SecondFragment();
                default:return new FirstFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


}