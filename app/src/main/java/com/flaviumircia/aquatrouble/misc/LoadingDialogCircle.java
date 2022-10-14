package com.flaviumircia.aquatrouble.misc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.flaviumircia.aquatrouble.R;

public class LoadingDialogCircle {
    private Context context;
    private Dialog dialog;

    public LoadingDialogCircle(Context context) {
        this.context = context;
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.layout_for_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }
    public void showDialog(String title){
        dialog.create();
        dialog.show();
    }
    public void showDialog(){
        dialog.setContentView(R.layout.layout_for_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.create();
        dialog.show();
    }
    public void hideDialog(){
        dialog.dismiss();
    }
}
