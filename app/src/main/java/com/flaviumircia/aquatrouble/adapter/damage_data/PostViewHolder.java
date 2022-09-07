package com.flaviumircia.aquatrouble.adapter.damage_data;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private TextView street_address;
    private TextView street_numbers;


    private TextView damages;
    private AppCompatImageButton see_details;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        street_address=itemView.findViewById(R.id.street_title);
        damages=itemView.findViewById(R.id.damages_value);
        see_details=itemView.findViewById(R.id.show_on_map);
        street_numbers=itemView.findViewById(R.id.street_no_details_adapter);
    }

    public TextView getDamages() {
        return damages;
    }

    public void setDamages(TextView damages) {
        this.damages = damages;
    }
    public ImageButton getSee_details() {
        return see_details;
    }

    public void setSee_details(AppCompatImageButton see_details) {
        this.see_details = see_details;
    }

    public TextView getStreet_address() {
        return street_address;
    }

    public void setStreet_address(TextView street_address) {
        this.street_address = street_address;
    }

    public TextView getStreet_numbers() {
        return street_numbers;
    }

    public void setStreet_numbers(TextView street_numbers) {
        this.street_numbers = street_numbers;
    }
}
