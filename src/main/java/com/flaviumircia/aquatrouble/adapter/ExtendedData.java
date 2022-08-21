package com.flaviumircia.aquatrouble.adapter;

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
                this.sum+=this.data.get(i).getFrequency();
        }
        return this.sum;
    }
}
