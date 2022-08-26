package com.flaviumircia.aquatrouble.adapter.sector_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.flaviumircia.aquatrouble.R;
import com.flaviumircia.aquatrouble.restdata.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SectorDataAdapterFiltered extends BaseAdapter implements Filterable {
    private List<Data> sectorData;
    private List<Data> sectorDataFiltered;
    private Context context;
    private LayoutInflater layoutInflater;
    public SectorDataAdapterFiltered(List<Data> sectorData, Context context) {
        this.sectorData = sectorData;
        this.sectorDataFiltered = sectorDataFiltered;
        this.context = context;
    }


    @Override
    public int getCount() {
        return sectorDataFiltered.size();
    }

    @Override
    public Object getItem(int i) {
        return sectorDataFiltered.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecyclerView.ViewHolder holder=null;
        if(view==null)
        {
            view=layoutInflater.inflate(R.layout.filter_inflater,null);
            holder=new RecyclerView.ViewHolder(view) {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
            view.setTag(holder);
        }
        else
        {
            holder=(RecyclerView.ViewHolder) view.getTag();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, sectorDataFiltered.get(i).getAddress(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results=new FilterResults();
                ArrayList<Data> filteredArrList=new ArrayList<Data>();

                if(sectorData==null)
                {
                    sectorData=new ArrayList<Data>(sectorDataFiltered);
                }
                if(charSequence== null || charSequence.length()==0)
                {
                    results.count=sectorData.size();
                    results.values=sectorData;
                }
                else
                {
                    charSequence=charSequence.toString().toLowerCase(Locale.ROOT);
                    for(int i=0;i<sectorData.size();i++)
                    {
                        String data=sectorData.get(i).getAddress();
                        if(data.toLowerCase(Locale.ROOT).startsWith(charSequence.toString()))
                        {   Data temp_data=sectorData.get(i);
                            filteredArrList.add(new Data(temp_data.getAddress()));

                        }
                    }
                    results.count=filteredArrList.size();
                    results.values=filteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sectorDataFiltered=(ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
