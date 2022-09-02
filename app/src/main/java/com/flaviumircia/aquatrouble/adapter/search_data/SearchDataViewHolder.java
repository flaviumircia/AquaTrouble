package com.flaviumircia.aquatrouble.adapter.search_data;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;

public class SearchDataViewHolder extends RecyclerView.ViewHolder {
    private TextView title,content;
    private ConstraintLayout background;
    private int color;
    public SearchDataViewHolder(@NonNull View itemView) {
        super(itemView);
        title= itemView.findViewById(R.id.title_search);
        content=itemView.findViewById(R.id.content_search);
        background=itemView.findViewById(R.id.background_layout_search);
        int color = Color.TRANSPARENT;
        Drawable background_drawable = background.getBackground();
        if (background_drawable instanceof ColorDrawable)
            this.color = ((ColorDrawable) background_drawable).getColor();
    }

    public int getColor() {
        return color;
    }

    public ConstraintLayout getBackground() {
        return background;
    }

    public void setBackground(ConstraintLayout background) {
        this.background = background;
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }
}
