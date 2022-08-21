package com.flaviumircia.aquatrouble.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private TextView street_address,street_numbers;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        street_address=itemView.findViewById(R.id.txtTitle);
        street_numbers=itemView.findViewById(R.id.txtContent);
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
