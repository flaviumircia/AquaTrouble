package com.flaviumircia.aquatrouble.adapter.damage_data;

import com.flaviumircia.aquatrouble.restdata.model.Data;

import java.util.List;

public class ExtendedData {

    List<Data> data;
    int sum;

    public ExtendedData(List<Data> data){
        this.data=data;
    }

    public int getTheTotalDamage(){
        for(int i=0;i<data.size();i++){
                this.sum+=this.data.get(i).getCount();
        }
        return this.sum;
    }
}
