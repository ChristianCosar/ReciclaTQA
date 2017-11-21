package com.kodevian.reciclapp.presenter;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.request.CompanyRequest;
import com.kodevian.reciclapp.request.GPSRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.request.tracks.TrackCompanyHolder;
import com.kodevian.reciclapp.request.tracks.TrackLocationHolder;
import com.kodevian.reciclapp.views.commons.GPSCompaniesView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by junior on 10/05/16.
 */
public class GPSCompaniesPresenter {
    GPSCompaniesView gpsCompaniesView;
    String next;

    public GPSCompaniesPresenter(GPSCompaniesView gpsCompaniesView) {
        this.gpsCompaniesView = gpsCompaniesView;
    }
    public void getCompaniesGPS(Location location){
        final GPSRequest gpsRequest =
                ServiceGeneratorSimple.createService(GPSRequest.class);
        Call<TrackCompanyHolder> call = gpsRequest.getCompaniesGPS(
                String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));

        call.enqueue(new Callback<TrackCompanyHolder>() {
            @Override
            public void onResponse(Response<TrackCompanyHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackCompanyHolder trackCompanyHolder= response.body();
                    next = trackCompanyHolder.getNext();
                    populate(trackCompanyHolder.getResults());
                } else {

                    gpsCompaniesView.setError("Ocurrio un error al traer la información.");
                }
            }

            @Override public void onFailure(Throwable t) {

                gpsCompaniesView.setError("Ocurrio un error al traer la información");
            }
        });
    }

    private void populate(List<CompanyEntity> locations)
    {
        gpsCompaniesView.populate(new ArrayList<CompanyEntity>(locations));
    }

}
