package com.flaviumircia.aquatrouble.menufragments;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentDamage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentDamage extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        check_theme();
        return inflater.inflate(R.layout.fragment_current_damage, container, false);
    }
    private void check_theme() {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
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