package com.flaviumircia.aquatrouble.menufragments;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.theme.ThemeModeChecker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favorites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorites extends Fragment implements ThemeModeChecker {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Window window=getActivity().getWindow();
        int nightModeFlags = getActivity().getResources().getConfiguration().uiMode &
                        android.content.res.Configuration.UI_MODE_NIGHT_MASK;

        setCustomTheme(window,nightModeFlags);

        return inflater.inflate(R.layout.fragment_favorites, container, false);
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