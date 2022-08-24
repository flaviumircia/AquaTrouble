package com.flaviumircia.aquatrouble.adapter.sector_data;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.restdata.model.ExtendedData;

public class SectorDataViewHolder extends RecyclerView.ViewHolder {
    private TextView street_title,street_number;
    private ExtendedData extendedData;
    private ImageButton imageButton;
    public SectorDataViewHolder(@NonNull View itemView) {
        super(itemView);
        street_title=itemView.findViewById(R.id.txtTitle);
        street_number=itemView.findViewById(R.id.txtContent);
        imageButton=itemView.findViewById(R.id.seeDetailsArrow);
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }


    public ExtendedData getExtendedData() {
        return extendedData;
    }

    public void setExtendedData(ExtendedData extendedData) {
        this.extendedData = extendedData;
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
