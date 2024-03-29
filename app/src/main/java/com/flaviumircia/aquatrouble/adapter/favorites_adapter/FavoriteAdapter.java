package com.flaviumircia.aquatrouble.adapter.favorites_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.database.NotificationsModel;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {
    private Context context;
    private List<NotificationsModel> list_of_model;
    public FavoriteAdapter(Context context, List<NotificationsModel> list_of_model) {
        this.context = context;
        this.list_of_model = list_of_model;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_layout,parent,false);
        return new FavoritesViewHolder(view);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        holder.getStreet_address().setText(String.valueOf(list_of_model.get(position).getAddress()));
        holder.getStreet_no().setText(new StringBuilder().append(context.getString(R.string.street_numbers)).append(": ").append(list_of_model.get(holder.getAdapterPosition()).getStreet_no()).toString());
        int nightModeFlags=context.getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        setTint(nightModeFlags,holder);
        holder.getBell().setOnClickListener(view ->{
            if(holder.isState()){
                holder.getBell().setBackground(context.getDrawable(R.drawable.ic_notifications_off));
                holder.setState(false);
                Toast.makeText(context, R.string.delete_this_address, Toast.LENGTH_SHORT).show();
                setTint(nightModeFlags,holder);
            }
            else {
                    holder.getBell().setBackground(context.getDrawable(R.drawable.ic_icon_favorite));
                    holder.setState(true);
                    setTint(nightModeFlags,holder);

            }});
    }
    public void setTint(int nightModeFlags,FavoritesViewHolder holder){
        if(nightModeFlags== Configuration.UI_MODE_NIGHT_YES)
            holder.getBell().getBackground().setTint(ContextCompat.getColor(context.getApplicationContext(),R.color.white));
        else if(nightModeFlags == Configuration.UI_MODE_NIGHT_NO)
            holder.getBell().getBackground().setTint(ContextCompat.getColor(context.getApplicationContext(),R.color.black));
        else
            holder.getBell().getBackground().setTint(ContextCompat.getColor(context.getApplicationContext(),R.color.black));
    }
    @Override
    public int getItemCount() {
        return list_of_model.size();
    }


}
