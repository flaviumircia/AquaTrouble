package com.flaviumircia.aquatrouble.adapter.damage_data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.StreetDetailsMap;
import com.flaviumircia.aquatrouble.restdata.model.Data;

import org.osmdroid.views.MapView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private Context context;
    private List<Data> postData;
    private MapView mapview;

    public PostAdapter(Context context, List<Data> postData,MapView mapview) {
        this.context = context;
        this.postData = postData;
        this.mapview=mapview;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_for_streets,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.getStreet_address().setText(String.valueOf(postData.get(position).getAddress()));
        holder.getStreet_numbers().setText(context.getString(R.string.frequency)+" " + String.valueOf(postData.get(position).getCount())+" "+ context.getString(R.string.times));
        Intent myIntent=new Intent(context, StreetDetailsMap.class);
        int resource_id=0;
        resource_id=getResourceId(postData.get(position).getSector());
        myIntent.putExtra("sector",postData.get(position).getSector());
        myIntent.putExtra("street_title",postData.get(position).getAddress());
        myIntent.putExtra("street_number",postData.get(position).getConcatanated_numbers());
        myIntent.putExtra("frequency",postData.get(position).getCount());
        myIntent.putExtra("affected_agent",postData.get(position).getAffected_agent());
        myIntent.putExtra("lat",postData.get(position).getLat());
        myIntent.putExtra("lng",postData.get(position).getLng());
        myIntent.putExtra("icon_id",resource_id);

        holder.getSee_details().setOnClickListener(view1 ->{
            context.startActivity(myIntent);
        });
    }

    @Override
    public int getItemCount() {
        return postData.size();
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

    public List<Data> getPostData() {
        return postData;
    }
}
