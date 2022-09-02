package com.flaviumircia.aquatrouble.adapter.damage_data;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private TextView street_address,street_numbers;
    private ImageButton see_details;
    private ConstraintLayout cardViewBackground;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        street_address=itemView.findViewById(R.id.txtTitle);
        street_numbers=itemView.findViewById(R.id.txtContent);
        see_details=itemView.findViewById(R.id.seeDetailsArrow);
        cardViewBackground=itemView.findViewById(R.id.constraintLayout);
    }

    public ImageButton getSee_details() {
        return see_details;
    }


    public ConstraintLayout getCardViewBackground() {
        return cardViewBackground;
    }

    public void setCardViewBackground(ConstraintLayout cardViewBackground) {
        this.cardViewBackground = cardViewBackground;
    }
    public void setSee_details(ImageButton see_details) {
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
