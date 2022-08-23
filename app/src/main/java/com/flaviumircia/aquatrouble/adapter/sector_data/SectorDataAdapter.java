package com.flaviumircia.aquatrouble.adapter.sector_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.restdata.model.Data;

import java.util.List;

public class SectorDataAdapter extends RecyclerView.Adapter<SectorDataViewHolder> {
    private Context context;
    private List<Data> sectorData;

    public SectorDataAdapter(Context context, List<Data> sectorData) {
        this.context = context;
        this.sectorData = sectorData;
    }

    @NonNull
    @Override
    public SectorDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_for_streets,parent,false);
        return new SectorDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectorDataViewHolder holder, int position) {

        holder.getStreet_title().setText(String.valueOf(sectorData.get(position).getAddress()));
        holder.getStreet_number().setText(String.valueOf(sectorData.get(position).getNumar()));

    }

    @Override
    public int getItemCount() {
        return sectorData.size();
    }
}
