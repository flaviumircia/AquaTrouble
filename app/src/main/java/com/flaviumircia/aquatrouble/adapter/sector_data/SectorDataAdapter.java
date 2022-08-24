package com.flaviumircia.aquatrouble.adapter.sector_data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.StreetDetails;
import com.flaviumircia.aquatrouble.misc.CurrentDate;
import com.flaviumircia.aquatrouble.misc.DateDiff;
import com.flaviumircia.aquatrouble.restdata.model.Data;

import java.util.List;

public class SectorDataAdapter extends RecyclerView.Adapter<SectorDataViewHolder> {
    private final Context context;
    private final List<Data> sectorData;
    private final int resource_id;
    public SectorDataAdapter(Context context, List<Data> sectorData,int resource_id) {
        this.context = context;
        this.sectorData = sectorData;
        this.resource_id=resource_id;
    }

    @NonNull
    @Override
    public SectorDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_for_streets,parent,false);
        CardView cardView=view.findViewById(R.id.cardViewContent);
        return new SectorDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectorDataViewHolder holder, int position) {
        CurrentDate currentDate=new CurrentDate();
        holder.getStreet_title().setText(String.valueOf(sectorData.get(position).getAddress()));
        holder.getStreet_number().setText(String.valueOf(sectorData.get(position).getNumar()));
        Intent myIntent=new Intent(context, StreetDetails.class);

        DateDiff dateDiff=new DateDiff(sectorData.get(position).getExpected_date(),currentDate.getCurrent_date());
        long diff=dateDiff.makeDifference();

        String days_until_finished=String.valueOf(diff/1000/60/60/24);

        myIntent.putExtra("sector",sectorData.get(position).getSector());
        myIntent.putExtra("street_title",sectorData.get(position).getAddress());
        myIntent.putExtra("street_number",sectorData.get(position).getNumar());
        myIntent.putExtra("expected_date",sectorData.get(position).getExpected_date());
        myIntent.putExtra("affected_agent",sectorData.get(position).getAffected_agent());
        myIntent.putExtra("remaining_days",days_until_finished);
        myIntent.putExtra("icon_id",this.resource_id);
        holder.getImageButton().setOnClickListener(view -> {
                        context.startActivity(myIntent);

        });

    }

    @Override
    public int getItemCount() {
        return sectorData.size();
    }
}
