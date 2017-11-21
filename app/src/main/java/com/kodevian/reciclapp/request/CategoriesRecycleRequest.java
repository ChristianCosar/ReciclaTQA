package com.kodevian.reciclapp.request;

import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.model.RecycleItemTrackCategoryAdd;
import com.kodevian.reciclapp.request.tracks.TrackCompanyHolder;
import com.kodevian.reciclapp.request.tracks.TrackItemRecycleHolder;

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
import retrofit.http.Query;


/**
 * Created by junior on 06/04/16.
 */
public interface CategoriesRecycleRequest {
    @GET("select-categories/")
    Call<TrackItemRecycleHolder> getCategories();

    @DELETE("delete-category-company/{id}/")
    Call<Void> deleteCategorie(@Header("Authorization") String token,@Path("id") String id);


    @FormUrlEncoded
    @PUT("update-category-company/{id}/")
    Call<RecycleItemCategorieTrack> editPrice(@Header("Authorization") String token,
                                                    @Path("id") String id,@Field("price") String price);

    @GET("categories-not-in-company/{id}/")
    Call<TrackItemRecycleHolder> getCategoriesNotInCompany(@Header("Authorization") String token,@Path("id") String id);

    @GET("search-list-companies/")
    Call<TrackCompanyHolder> filterCompanies(@Query("categories") String id,@Query("reciclap") boolean checkR,
                                                 @Query("juridic") boolean checkJ ,@Query("verify") boolean checkV,
                                                 @Query("latitude") double latitude,@Query("longitude") double longitude);
    @GET("search-list-companies/")
    Call<TrackCompanyHolder> filterCompanies(@Query("categories") String id,@Query("reciclap") boolean checkR,
                                             @Query("juridic") boolean checkJ ,@Query("verify") boolean checkV);
    @FormUrlEncoded
    @POST("add-category-company/")
    Call<RecycleItemTrackCategoryAdd> addCategory(@Header("Authorization") String token,@Field("category") String id,
                                                @Field("company") String idCompany,@Field("price") String price);
}
