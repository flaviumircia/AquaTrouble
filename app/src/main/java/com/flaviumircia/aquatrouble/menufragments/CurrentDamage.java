package com.flaviumircia.aquatrouble.menufragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.Search;
import com.flaviumircia.aquatrouble.Sector;
import com.flaviumircia.aquatrouble.misc.NetworkChecker;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
import com.flaviumircia.aquatrouble.theme.ThemeModeChecker;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentDamage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentDamage extends Fragment implements ThemeModeChecker {
    private final String file="LANGUAGE_PREF";
    private BannerView bannerView;
    private NetworkChecker networkChecker;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public CurrentDamage() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentDamage.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentDamage newInstance(String param1, String param2) {
        CurrentDamage fragment = new CurrentDamage();
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
        networkChecker=new NetworkChecker(requireContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HwAds.init(requireContext());
        bannerView=view.findViewById(R.id.hw_banner_view);
        Button s1=view.findViewById(R.id.sector1);
        Button s2=view.findViewById(R.id.sector2);
        Button s3=view.findViewById(R.id.sector3);
        Button s4=view.findViewById(R.id.sector4);
        Button s5=view.findViewById(R.id.sector5);
        Button s6=view.findViewById(R.id.sector6);
        ImageButton search_button=view.findViewById(R.id.search_button);
        searchOnClick(search_button);
        if(networkChecker.isNetworkAvailable())
            buttonsListeners(s1,s2,s3,s4,s5,s6);
        else
            Toast.makeText(requireContext(), "Network connection should be active!", Toast.LENGTH_SHORT).show();


        //bannerview settings
        bannerView.setAdId("v1fp3llla2");

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
                Log.d("Current Damage", "onAdLoaded: ");
            }

            @Override
            public void onAdFailed(int i) {
                super.onAdFailed(i);
                Log.d("Current Damage", "onAdFailed: "+i);

            }
        });
    }

    private void searchOnClick(ImageButton search_button) {
        search_button.setOnClickListener(view -> {
            Intent myIntent=new Intent(getContext(),Search.class);
            startActivity(myIntent);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Window window=getActivity().getWindow();
        int nightModeFlags=getActivity().getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        setCustomTheme(window,nightModeFlags);
        return inflater.inflate(R.layout.fragment_current_damage, container, false);
    }
    private void buttonsListeners(Button s1, Button s2, Button s3, Button s4, Button s5, Button s6) {
        s1.setOnClickListener(view -> {
            Intent myIntent=new Intent(getActivity(), Sector.class);
            myIntent.putExtra("sector","Sector 1");
            myIntent.putExtra("icon",R.drawable.ic_arc_triumf);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(myIntent);
        });
        s2.setOnClickListener(view -> {
            Intent myIntent=new Intent(getActivity(), Sector.class);
            myIntent.putExtra("sector","Sector 2");
            myIntent.putExtra("icon",R.drawable.ic_roata_mare);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(myIntent);
        });
        s3.setOnClickListener(view -> {
            Intent myIntent=new Intent(getActivity(), Sector.class);
            myIntent.putExtra("sector","Sector 3");
            myIntent.putExtra("icon",R.drawable.ic_parc_ior);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(myIntent);
        });
        s4.setOnClickListener(view -> {
            Intent myIntent=new Intent(getActivity(), Sector.class);
            myIntent.putExtra("sector","Sector 4");
            myIntent.putExtra("icon",R.drawable.ic_mausoleu);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(myIntent);
        });
        s5.setOnClickListener(view -> {
            Intent myIntent=new Intent(getActivity(), Sector.class);
            myIntent.putExtra("sector","Sector 5");
            myIntent.putExtra("icon",R.drawable.ic_palat_parlament);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(myIntent);
        });
        s6.setOnClickListener(view -> {
            Intent myIntent=new Intent(getActivity(), Sector.class);
            myIntent.putExtra("sector","Sector 6");
            myIntent.putExtra("icon",R.drawable.ic_lacul_morii);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(myIntent);
        });
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