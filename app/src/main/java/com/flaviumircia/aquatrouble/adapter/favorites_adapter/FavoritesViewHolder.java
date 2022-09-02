package com.flaviumircia.aquatrouble.adapter.favorites_adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class FavoritesViewHolder extends RecyclerView.ViewHolder {
    private TextView street_address;


    private TextView street_no;
    private ImageButton bell;
    private boolean state;
    public FavoritesViewHolder(@NonNull View itemView) {
        super(itemView);
        street_address=itemView.findViewById(R.id.title_text);
        street_no=itemView.findViewById(R.id.street_no);
        bell=itemView.findViewById(R.id.bellIcon);
        state=true;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public TextView getStreet_no() {
        return street_no;
    }

    public void setStreet_no(TextView street_no) {
        this.street_no = street_no;
    }

    public ImageButton getBell() {
        return bell;
    }

    public void setBell(ImageButton bell) {
        this.bell = bell;
    }

    public TextView getStreet_address() {
        return street_address;
    }

    public void setStreet_address(TextView street_address) {
        this.street_address = street_address;
    }


}
