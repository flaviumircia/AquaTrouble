package com.flaviumircia.aquatrouble.adapter.sector_data;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class SectorDataViewHolder extends RecyclerView.ViewHolder {
    private TextView street_title,street_number;
    public SectorDataViewHolder(@NonNull View itemView) {
        super(itemView);
        street_title=itemView.findViewById(R.id.txtTitle);
        street_number=itemView.findViewById(R.id.txtContent);
    }

    public TextView getStreet_title() {
        return street_title;
    }

    public void setStreet_title(TextView street_title) {
        this.street_title = street_title;
    }

    public TextView getStreet_number() {
        return street_number;
    }

    public void setStreet_number(TextView street_number) {
        this.street_number = street_number;
    }
}
