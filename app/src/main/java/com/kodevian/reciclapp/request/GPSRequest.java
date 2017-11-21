package com.kodevian.reciclapp.request;

import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.request.tracks.TrackCompanyHolder;
import com.kodevian.reciclapp.request.tracks.TrackLocationHolder;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Created by junior on 10/05/16.
 */
public interface GPSRequest {
    @GET("search-geo-companies/")
    Call<TrackCompanyHolder> getCompaniesGPS(@Query("latitude") String latitude,@Query("longitude") String longitude);
}
