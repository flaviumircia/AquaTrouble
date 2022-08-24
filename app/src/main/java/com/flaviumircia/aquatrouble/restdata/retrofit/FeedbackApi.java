package com.flaviumircia.aquatrouble.restdata.retrofit;

import com.flaviumircia.aquatrouble.restdata.model.FeedbackModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeedbackApi {
    @POST("street/feedback/feedback/{subject}/{content}/{sender}/")
    Observable<FeedbackModel> postFeedback(@Path(value="subject",encoded = true) String subject,
                                      @Path(value="content",encoded = true) String content,
                                      @Path(value="sender",encoded = true) String sender);
}
