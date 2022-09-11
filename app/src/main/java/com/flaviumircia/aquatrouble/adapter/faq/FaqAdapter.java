package com.flaviumircia.aquatrouble.adapter.faq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.adapter.favorites_adapter.FavoritesViewHolder;
import com.flaviumircia.aquatrouble.restdata.model.Data;
import com.flaviumircia.aquatrouble.settings_pref_activities.models.FaqModel;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqViewHolder> {
    private Context context;
    private List<FaqModel> data;
    private boolean isPressed;
    public FaqAdapter(Context context, List<FaqModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dropdown_layout,parent,false);
        return new FaqViewHolder(view);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        holder.getQuestion_title().setText(data.get(holder.getAdapterPosition()).getQuestion_title());
        holder.getContent().setVisibility(View.GONE);
        holder.getContent().setText("");
        holder.getSee_more().setOnClickListener(view -> {
            if(holder.isState()) {
                holder.getContent().setVisibility(View.VISIBLE);
                holder.getContent().setText(data.get(holder.getAdapterPosition()).getQuestion_content());
                holder.setState(false);
            }else{
                holder.getContent().setVisibility(View.GONE);
                holder.getContent().setText("");
                holder.setState(true);
            }

        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
