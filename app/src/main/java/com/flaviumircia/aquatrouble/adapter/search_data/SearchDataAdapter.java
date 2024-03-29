package com.flaviumircia.aquatrouble.adapter.search_data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.StreetDetails;
import com.flaviumircia.aquatrouble.misc.CurrentDate;
import com.flaviumircia.aquatrouble.misc.DateDiff;
import com.flaviumircia.aquatrouble.misc.PreferenceLanguageSetter;
import com.flaviumircia.aquatrouble.restdata.model.Data;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataViewHolder> {
    private final Context context;
    private final List<Data> sectorData;
    private List<Data> sectorDataCopy;

    public SearchDataAdapter(Context context, List<Data> sectorData) {
        this.context = context;
        this.sectorData = sectorData;
        sectorDataCopy=new ArrayList<Data>();
        this.sectorDataCopy.addAll(this.sectorData);

    }

    @NonNull
    @Override
    public SearchDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.current_damage_street_layout,parent,false);
        return new SearchDataViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull SearchDataViewHolder holder, int position) {
        CurrentDate currentDate=new CurrentDate();
        holder.getStreet_title().setText(String.valueOf(sectorData.get(position).getAddress()));
        holder.getStreet_number().setText(String.valueOf(sectorData.get(position).getNumar()));
        Intent myIntent=new Intent(context, StreetDetails.class);

        DateDiff dateDiff=new DateDiff(sectorData.get(position).getExpected_date(),currentDate.getCurrent_date());
        long diff=dateDiff.makeDifference();

        String days_until_finished=String.valueOf(diff/1000/60/60/24);
        int resourceId=getResourceId(sectorData.get(position).getSector());
        myIntent.putExtra("sector",sectorData.get(position).getSector());
        myIntent.putExtra("street_title",sectorData.get(position).getAddress());
        myIntent.putExtra("street_number",sectorData.get(position).getNumar());
        myIntent.putExtra("expected_date",sectorData.get(position).getExpected_date());
        myIntent.putExtra("affected_agent",sectorData.get(position).getAffected_agent());
        myIntent.putExtra("remaining_days",days_until_finished);
        myIntent.putExtra("lat",sectorData.get(position).getLatitude());
        myIntent.putExtra("lng",sectorData.get(position).getLongitude());

        myIntent.putExtra("icon_id",resourceId);
        holder.getDays_until().setText(days_until_finished);

        holder.getSee_details().setOnClickListener(view -> {
            context.startActivity(myIntent);
        });
    }

    private int getResourceId(String sector) {
        switch (sector)
        {
            case "1":
                return R.drawable.ic_arc_triumf;
            case "2":
                return R.drawable.ic_roata_mare;
            case "3":
                return R.drawable.ic_parc_ior;
            case "4":
                return R.drawable.ic_mausoleu;
            case "5":
                return R.drawable.ic_palat_parlament;
            case "6":
                return R.drawable.ic_lacul_morii;
        }
        return R.drawable.ic_arc_triumf;
    }

    @Override
    public int getItemCount() {
        return sectorData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        sectorData.clear();
        if(text.isEmpty()){
            sectorData.addAll(sectorDataCopy);
        }else{
            text = text.toLowerCase();
            for(Data item:sectorDataCopy){
                String deaccent_address=deAccent(item.getAddress().toLowerCase(Locale.ROOT));
                if(deaccent_address.contains(text) || item.getAddress().toLowerCase().contains(text)){
                    sectorData.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


    private String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
