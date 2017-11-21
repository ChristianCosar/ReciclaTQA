package com.kodevian.reciclapp.request;

import com.kodevian.reciclapp.model.AccessTokenEntity;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 Created by junior on 24/04/16.
 */
public interface LoginRequest {

    @FormUrlEncoded
    @POST("login/")
    Call<AccessTokenEntity> basicLogin(@Field("email") String username,
                                       @Field("password") String password);
    @FormUrlEncoded
    @POST("login/mobile/facebook/")
    Call<AccessTokenEntity> loginFacebook(@Field("access_token") String tokenFace);

}
