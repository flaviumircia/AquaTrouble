package com.flaviumircia.aquatrouble.restdata.retrofit;

import com.flaviumircia.aquatrouble.restdata.model.Data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SectorDataApi {
    @GET("street/sector_data/?format=json")
    Observable<List<Data>> getData(@Query("sector") int sector);
}
