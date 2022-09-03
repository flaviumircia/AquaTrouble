package com.flaviumircia.aquatrouble.menufragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.menufragments.mapfragments.OsmdroidMap;
import com.flaviumircia.aquatrouble.menufragments.mapfragments.WebPageMap;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //from activity
    private SegmentedGroup aSwitch;
    private int map_status;
    private ImageButton search;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        //set the language
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        aSwitch=v.findViewById(R.id.switchHeat);
        aSwitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton= (RadioButton) radioGroup.findViewById(i);
                boolean isChecked=checkedRadioButton.isChecked();

                if(isChecked && (checkedRadioButton.getText().equals(getActivity().getString(R.string.pornit))))
                {   map_status=R.id.on_heat;
                    replaceFragment(new WebPageMap());
                }
                else if(isChecked && (checkedRadioButton.getText().equals(getActivity().getString(R.string.oprit))))
                {   map_status=R.id.off_heat;
                    replaceFragment(new OsmdroidMap());
                }
            }
        });
        if(savedInstanceState!=null)
        {
            map_status=savedInstanceState.getInt("map_status");

            if(map_status==R.id.on_heat)
            {
                replaceFragment(new WebPageMap());
            }
            else if(map_status==R.id.off_heat)
            {
                replaceFragment(new OsmdroidMap());
            }
        }
        else
        {
            map_status=R.id.off_heat;
            replaceFragment(new OsmdroidMap());}
        return v;
    }



    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutInHome,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("map_status",map_status);
    }
}