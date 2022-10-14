package com.flaviumircia.aquatrouble.settings_pref_activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.flaviumircia.aquatrouble.LanguageSetter;
import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.misc.LoadingDialogCircle;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;

public class MakeDonation extends AppCompatActivity {
    private ImageButton back_arrow;
    private final String file="LANGUAGE_PREF";
    private LoadingDialogCircle dialogCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceLanguageSetter preferenceLanguageSetter=new PreferenceLanguageSetter(this,file);
        preferenceLanguageSetter.setTheLanguage();
        setContentView(R.layout.activity_make_donation);
        back_arrow=findViewById(R.id.backArrowMakeDonation);
        dialogCircle=new LoadingDialogCircle(this);
        back_arrow.setOnClickListener(view->{finish();});
        WebView myWebView = (WebView) findViewById(R.id.webViewDonation);

        // chromium, enable hardware acceleration
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setSupportZoom(true);
        myWebView.requestFocus();
        myWebView.setPadding(0, 0, 0, 0);
        webSettings.setJavaScriptEnabled(true);

        myWebView.loadUrl("https://www.paypal.com/donate/?hosted_button_id=RX6GQH6WSP74G");
        myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress<100){
                    dialogCircle.showDialog();

                }else if (newProgress==100){
                    dialogCircle.hideDialog();
                }
            }
        });
    }
}