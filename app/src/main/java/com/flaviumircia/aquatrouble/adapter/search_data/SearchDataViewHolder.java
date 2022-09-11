package com.flaviumircia.aquatrouble.adapter.search_data;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class SearchDataViewHolder extends RecyclerView.ViewHolder {
    private TextView street_title,street_number;
    protected TextView days_until;
    private AppCompatButton see_details;
    public SearchDataViewHolder(@NonNull View itemView) {
        super(itemView);
        street_title= itemView.findViewById(R.id.street_title_current_damage);
        street_number=itemView.findViewById(R.id.street_no_current_damage);
        see_details=itemView.findViewById(R.id.see_more_details);
        days_until=itemView.findViewById(R.id.days_left);
//        int color = Color.TRANSPARENT;
//        Drawable background_drawable = background.getBackground();
//        if (background_drawable instanceof ColorDrawable)
//            this.color = ((ColorDrawable) background_drawable).getColor();
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

    public TextView getDays_until() {
        return days_until;
    }

    public void setDays_until(TextView days_until) {
        this.days_until = days_until;
    }

    public AppCompatButton getSee_details() {
        return see_details;
    }

    public void setSee_details(AppCompatButton see_details) {
        this.see_details = see_details;
    }
}
