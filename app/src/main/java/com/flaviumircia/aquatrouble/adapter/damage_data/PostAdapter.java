package com.flaviumircia.aquatrouble.adapter.damage_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.restdata.model.Data;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private Context context;
    private List<Data> postData;

    public PostAdapter(Context context, List<Data> postData) {
        this.context = context;
        this.postData = postData;
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
        holder.getStreet_numbers().setText("Afectata de " + String.valueOf(postData.get(position).getFrequency())+ " ori");
    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    public List<Data> getPostData() {
        return postData;
    }
}
