package com.flaviumircia.aquatrouble.restdata.retrofit;

import com.flaviumircia.aquatrouble.restdata.model.FeedbackModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SpotABug {
    @POST("street/feedback/{phone_model}/{subject}/{content}/{sender}/")
    Observable<FeedbackModel> postBug(@Path(value="phone_model",encoded = true) String phone_model,
                                       @Path(value="subject",encoded = true) String subject,
                                       @Path(value="content",encoded = true) String content,
                                       @Path(value="sender",encoded = true) String sender);
//    Observable<FeedbackModel> postBug();
}
