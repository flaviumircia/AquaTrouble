package com.flaviumircia.aquatrouble.menufragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.adapter.favorites_adapter.FavoriteAdapter;
import com.flaviumircia.aquatrouble.adapter.favorites_adapter.FavoritesViewHolder;
import com.flaviumircia.aquatrouble.database.ActivityChecker;
import com.flaviumircia.aquatrouble.database.DaoClass;
import com.flaviumircia.aquatrouble.database.Database;
import com.flaviumircia.aquatrouble.database.DbExists;
import com.flaviumircia.aquatrouble.database.NotificationsModel;
import com.flaviumircia.aquatrouble.misc.CurrentTime;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
import com.flaviumircia.aquatrouble.theme.ThemeModeChecker;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favorites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorites extends Fragment implements ThemeModeChecker {
    private final String file="LANGUAGE_PREF";
    private Database database;
    private BannerView bannerView;
    private CompositeDisposable compositeDisposable;
    private DaoClass daoClass;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Window window;
    int nightModeFlags;

    public Favorites() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorites newInstance(String param1, String param2) {
        Favorites fragment = new Favorites();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(requireContext(),file);
        preferenceLanguageSetter.setTheLanguage();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.favorites_recyclerView);
        bannerView=view.findViewById(R.id.bannerView_favorites);
        bannerView.setAdId("x7aedjl3oq");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_320_50);
        bannerView.setBannerRefresh(60);
        AdParam adParam=new AdParam.Builder().build();
        bannerView.loadAd(adParam);
        bannerView.setAdListener(new AdListener(){
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("FAVORITES", "onAdLoaded: ");
            }

            @Override
            public void onAdFailed(int i) {
                super.onAdFailed(i);
                Log.d("FAVORITES", "onAdFailed: "+i);

            }
        });
        bannerViewSettings();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void bannerViewSettings() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         window=getActivity().getWindow();
         nightModeFlags = getActivity().getResources().getConfiguration().uiMode &
                        android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        CurrentTime currentDate=new CurrentTime();
        Log.d("TAG", "onCreateView: "+currentDate.getCurrent_time());
        setCustomTheme(window,nightModeFlags);
        DbExists dbExists=new DbExists();
        ActivityChecker activityChecker=new ActivityChecker();

        if(dbExists.doesDatabaseExists(getActivity().getApplicationContext(),"DATABASE") && activityChecker.checkNotifDatabase(getActivity().getApplicationContext())!=null)
        {
            compositeDisposable=new CompositeDisposable();
            database= Database.getDatabase(getActivity().getApplicationContext());
            daoClass=database.getDao();
            fetchData();
        }
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    private void fetchData() {
        compositeDisposable.add(daoClass.getAllNotifData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data->getTheData(data)));
    }

    private void getTheData(List<NotificationsModel> data) {
        FavoriteAdapter favoriteAdapter=new FavoriteAdapter(getContext(),data);
        recyclerView.setAdapter(favoriteAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG", "onStop: ");
        for(int i=0;i<recyclerView.getChildCount();i++){
            FavoritesViewHolder holder=(FavoritesViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if(holder!=null){
            if(!holder.isState()){
                String[] array=holder.getStreet_no().getText().toString().split(": ",2);

                daoClass.delete(holder.getStreet_address().getText().toString(),array[1]);

                recyclerView.removeViewAt(i);
            }

            }
        }
    }
    //TODO: Translate Oprire ACC/INC
    //TODO: Make EULA legal
    //TODO: Rotate bug at home
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