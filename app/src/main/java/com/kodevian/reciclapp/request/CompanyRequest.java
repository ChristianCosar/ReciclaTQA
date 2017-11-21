package com.kodevian.reciclapp.request;

import com.kodevian.reciclapp.model.AccessTokenEntity;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.CompanyJuridicEntity;
import com.kodevian.reciclapp.model.CompanyReciclappEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.model.LocationTrack;
import com.kodevian.reciclapp.model.TrackRecycleItemEntity;
import com.kodevian.reciclapp.model.UploadResponse;
import com.kodevian.reciclapp.request.tracks.TrackCompanyHolder;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Url;

/**
 * Created by junior on 24/04/16.
 */
public interface CompanyRequest {


    @POST("create-juridic-company/")
    Call<CompanyEntity> registerJuridicCompany(@Header("Authorization") String token, @Body CompanyEntity companyEntity);

    @POST("create-reciclapp-company/")
    Call<CompanyEntity> registerReciclappCompany(@Header("Authorization") String token, @Body CompanyEntity companyEntity);


    @GET("companies/")
    Call<TrackCompanyHolder>  getCompanies();

    @GET()
    Call<TrackCompanyHolder> getMoreCompanies(@Url String url);

    @GET("my-companies/")
    Call<TrackCompanyHolder>  getMyCompanies(@Header("Authorization") String token);

    @GET()
    Call<TrackCompanyHolder> getMoreMyCOmpanies(@Header("Authorization") String token, @Url String url);

    @PUT("update-company-photo/{id}/")
    Call<CompanyEntity> updateCompanyPhotos(@Header("Authorization") String token,@Path("id") String idCompany,@Body RequestBody body);

    @FormUrlEncoded
    @PUT("update-company/{id}/")
    Call<CompanyEntity> updateAddress(@Header("Authorization") String token,@Path("id") String idCompany,@Field("address") String address);


    @FormUrlEncoded
    @PUT("update-company/{id}/")
    Call<CompanyEntity> updateCellphone(@Header("Authorization") String token,@Path("id") String idCompany,@Field("celphone") String cellphone);


    @PUT("update-company/{id}/")
    Call<CompanyEntity> updateLocation(@Header("Authorization") String token,@Path("id") String idCompany,@Body LocationTrack location);

    @FormUrlEncoded
    @PUT("update-company/{id}/")
    Call<CompanyEntity> updatePhone(@Header("Authorization") String token,@Path("id") String idCompany,@Field("telephone") String telephone);

    @FormUrlEncoded
    @PUT("update-company/{id}/")
    Call<CompanyEntity> updateEmail(@Header("Authorization") String token,@Path("id") String idCompany,@Field("web") String email);

    @FormUrlEncoded
    @PUT("update-company/{id}/")
    Call<CompanyEntity> update_L_V(@Header("Authorization") String token,@Path("id") String idCompany,@Field("monday_to_friday") String monday_to_friday);

    @FormUrlEncoded
    @PUT("update-company/{id}/")
    Call<CompanyEntity> update_S_D(@Header("Authorization") String token,@Path("id") String idCompany,@Field("saturday_sunday_holidays") String saturday_sunday_holidays);

    @DELETE("delete-company/{id}/")
    Call<Void> deleteCompany(@Header("Authorization") String token,@Path("id") String idCompany);

}

