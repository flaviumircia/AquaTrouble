package com.flaviumircia.aquatrouble.adapter.faq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
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
        this.isPressed=false;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dropdown_layout,parent,false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        holder.getQuestion_title().setText(data.get(holder.getAdapterPosition()).getQuestion_title());
        holder.getContent().setVisibility(View.GONE);
        holder.getSee_more().setOnClickListener(view -> {
            holder.getContent().setVisibility(View.VISIBLE);
            holder.getContent().setText(data.get(holder.getAdapterPosition()).getQuestion_content());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
