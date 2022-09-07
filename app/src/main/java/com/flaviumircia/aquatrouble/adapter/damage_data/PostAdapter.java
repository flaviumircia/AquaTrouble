package com.flaviumircia.aquatrouble.adapter.damage_data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.restdata.model.Data;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private Context context;
    private List<Data> postData;
    private List<Data> postDataCopy;
    private MapView mapview;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    public PostAdapter(Context context, List<Data> postData) {
        this.context = context;
        this.postData = postData;
        this.postDataCopy=new ArrayList<>();
        this.postDataCopy.addAll(postData);
    }

    public PostAdapter(Context context, List<Data> postData, MapView mapview) {
        this.context = context;
        this.postData = postData;
        this.postDataCopy=new ArrayList<>();
        this.postDataCopy.addAll(postData);
        this.mapview=mapview;

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_for_streets,parent,false);
        return new PostViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.getStreet_address().setText(String.valueOf(postData.get(position).getAddress()));
        holder.getStreet_numbers().setText(context.getString(R.string.street_numbers)+": "+String.valueOf(postData.get(position).getConcatanated_numbers()));
        holder.getDamages().setText(String.valueOf(postData.get(position).getCount()));
        holder.getSee_details().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3EBE30")));

                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    view.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.light_blue)));
                    view.performClick();

                }else if(motionEvent.getAction()==MotionEvent.ACTION_CANCEL){
                    view.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.light_blue)));

                }
            return true;
            }
        });
        holder.getSee_details().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMapController mapController=mapview.getController();
                GeoPoint street_point=new GeoPoint(postData.get(holder.getAdapterPosition()).getLatitude(),postData.get(holder.getAdapterPosition()).getLongitude());
                mapController.setCenter(street_point);
                Marker marker=new Marker(mapview);
                marker.setPosition(street_point);
                mapview.getOverlays().add(marker);
            }
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
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        postData.clear();
        if(text.isEmpty()){
            postData.addAll(postDataCopy);
        }else{
            text = text.toLowerCase();
            for(Data item:postDataCopy){
                String deaccent_address=deAccent(item.getAddress().toLowerCase(Locale.ROOT));
                if(deaccent_address.contains(text) || item.getAddress().toLowerCase().contains(text)){
                    postData.add(item);
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
    public List<Data> getPostData() {
        return postData;
    }
}
