package com.kodevian.reciclapp.request;



import com.kodevian.reciclapp.model.AccessTokenEntity;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.UserEntity;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by junior on 24/04/16.
 */
public interface UserRequest {
    @GET("user/retrieve/")
    Call<UserEntity> getUser(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("register/")
    Call<AccessTokenEntity> registerUser(@Field("email") String email, @Field("password") String password, @Field("first_name") String first_name,
                                         @Field("last_name") String last_name, @Field("gender") String gender,
                                         @Field("celphone") String celphone, @Field("id_categories") String id_categories,
                                         @Field("birthdate") String birthdate);



    @POST("register/")
    Call<AccessTokenEntity> registerUser(@Body UserEntity userEntity);



    @FormUrlEncoded
    @PUT("user/update/{id}/")
    Call<UserEntity> editUser( @Header("Authorization")String token,@Field("first_name") String first_name, @Field("last_name") String last_name,
                               @Field("gender") String gender,@Field("celphone") String cellphone,
                               @Field("birthdate") String birthdate,@Path("id")String id);

}
