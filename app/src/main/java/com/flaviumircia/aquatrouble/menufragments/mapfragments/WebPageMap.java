package com.flaviumircia.aquatrouble.menufragments.mapfragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebPageMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebPageMap extends Fragment {
    private final String file="LANGUAGE_PREF";
    private String lang;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WebPageMap() {
        //set the language

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebPageMap.
     */
    // TODO: Rename and change types and number of parameters
    public static WebPageMap newInstance(String param1, String param2) {
        WebPageMap fragment = new WebPageMap();
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
        LanguageSetter languageSetter=new LanguageSetter();
        //set the language
        SharedPreferences sharedPreferences= getContext().getSharedPreferences(file,Context.MODE_PRIVATE);
        String lang=sharedPreferences.getString("lang",null);
        languageSetter.setLocale(lang,getContext());
        this.lang=lang;
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_webpage_map,container,false);
        WebView myWebView = (WebView) v.findViewById(R.id.webview);
        // chromium, enable hardware acceleration
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setSupportZoom(true);
        myWebView.requestFocus();
        myWebView.setPadding(0, 0, 0, 0);
        myWebView.setInitialScale(getScale());
        webSettings.setJavaScriptEnabled(true);
        check_theme(myWebView);
        return v;
    }
    private int getScale(){
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(650);
        val = val * 100d;
        return val.intValue();
    }
    private void check_theme(WebView myWebView) {

        int nightModeFlags =
                getActivity().getResources().getConfiguration().uiMode &
                        android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case android.content.res.Configuration.UI_MODE_NIGHT_YES:
                if(lang!=null && lang.equals("ro-Ro"))
                    myWebView.loadUrl("http://18.159.213.37/output_ro_dark.html");
                else
                    myWebView.loadUrl("http://18.159.213.37/output_dark_eng.html");
                break;
            case android.content.res.Configuration.UI_MODE_NIGHT_NO:
                if(lang!=null && lang.equals("ro-Ro"))
                    myWebView.loadUrl("http://18.159.213.37/output_ro.html");
                else
                    myWebView.loadUrl("http://18.159.213.37/output_eng.html");

                break;
            case android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED:
                if(lang!=null && lang.equals("en"))
                    myWebView.loadUrl("http://18.159.213.37/output_eng.html");
                else
                    myWebView.loadUrl("http://18.159.213.37/output_ro.html");

                break;
            default: myWebView.loadUrl("http://18.159.213.37/output_ro.html");
        }
    }
}