package com.kodevian.reciclapp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterCompanyItem;
import com.kodevian.reciclapp.request.CategoriesRecycleRequest;
import com.kodevian.reciclapp.request.CompanyRequest;
import com.kodevian.reciclapp.request.UserRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.request.tracks.TrackCompanyHolder;
import com.kodevian.reciclapp.request.tracks.TrackItemRecycleHolder;
import com.kodevian.reciclapp.views.commons.CompanyExploredView;
import com.kodevian.reciclapp.views.commons.MyCompaniesViews;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by junior on 25/04/16.
 */
public class CompanyExploredPresenter implements CommunicatePresenterCompanyItem {

    CompanyExploredView companyExploredView;
    Context context;
    String next;

    public CompanyExploredPresenter(Context context,CompanyExploredView companyExploredView) {
        this.companyExploredView = companyExploredView;
        this.context = context;
    }




    public void getCompanies(){
        final CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        companyExploredView.showLoad();
        Call<TrackCompanyHolder> call = companyRequest.getCompanies();

        call.enqueue(new Callback<TrackCompanyHolder>() {
            @Override
            public void onResponse(Response<TrackCompanyHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackCompanyHolder trackCompanyHolder= response.body();
                    next = trackCompanyHolder.getNext();
                    populate(trackCompanyHolder.getResults());
                } else {

                    companyExploredView.setError("Ocurrio un error al traer la información.");
                    companyExploredView.hideLoad();
                }
            }

            @Override public void onFailure(Throwable t) {

                companyExploredView.setError("Ocurrio un error al traer la información");
                companyExploredView.hideLoad();
            }
        });
    }

    public void getMore(){
        if(next!=null){
            if(!TextUtils.isEmpty(next)){
                final CompanyRequest companyRequest =
                        ServiceGeneratorSimple.createService(CompanyRequest.class);
                Call<TrackCompanyHolder> call = companyRequest.getMoreCompanies(next);
                call.enqueue(new Callback<TrackCompanyHolder>() {

                    @Override
                    public void onResponse(Response<TrackCompanyHolder> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            TrackCompanyHolder trackCompanyHolder= response.body();
                            next = trackCompanyHolder.getNext();
                            setMoreNext(trackCompanyHolder.getResults());
                        } else {
                            companyExploredView.hideLoad();
                            companyExploredView.setError("Ocurrio un error al traer la información");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        companyExploredView.hideLoad();
                        companyExploredView.setError("Ocurrio un error al traer la información");
                    }
                });
            }else {
                companyExploredView.hideLoad();
            }
        }else{
            companyExploredView.hideLoad();
        }




    }

    public void getCategories(){
        final CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        companyExploredView.showLoad();
        Call<TrackItemRecycleHolder> call = categoriesRecycleRequest.getCategories();
        call.enqueue(new Callback<TrackItemRecycleHolder>() {
            @Override
            public void onResponse(Response<TrackItemRecycleHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackItemRecycleHolder trackItemRecycleHolder= response.body();
                    next = trackItemRecycleHolder.getNext();
                    populateDialog(trackItemRecycleHolder.getResults());
                } else {
                    companyExploredView.hideLoad();
                    companyExploredView.setError("Ocurrio un error al traer la información");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                companyExploredView.hideLoad();
                companyExploredView.setError("Ocurrio un error en la conexión al servidor");
            }
        });
    }

    public void searchCompanies(String id,boolean r,boolean j,boolean verify,Location location){
        final CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        companyExploredView.showLoad();

        if(location!=null){

        }
        Call<TrackCompanyHolder> call = categoriesRecycleRequest.filterCompanies(id, r, j, verify,
                location.getLatitude(),
                location.getLongitude());
        call.enqueue(new Callback<TrackCompanyHolder>() {
            @Override
            public void onResponse(Response<TrackCompanyHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackCompanyHolder trackCompanyHolder= response.body();
                    next = trackCompanyHolder.getNext();
                    populate(trackCompanyHolder.getResults());
                } else {
                    companyExploredView.hideLoad();
                    companyExploredView.setError("Ocurrio un error al traer la información");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                companyExploredView.hideLoad();
                companyExploredView.setError("Ocurrio un error en la conexión al servidor");
            }
        });
    }
    public void searchCompanies(String id,boolean r,boolean j,boolean verify){
        final CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        companyExploredView.showLoad();


        Call<TrackCompanyHolder> call = categoriesRecycleRequest.filterCompanies(id,r,j,verify);
        call.enqueue(new Callback<TrackCompanyHolder>() {
            @Override
            public void onResponse(Response<TrackCompanyHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackCompanyHolder trackCompanyHolder= response.body();
                    next = trackCompanyHolder.getNext();
                    populate(trackCompanyHolder.getResults());
                } else {
                    companyExploredView.hideLoad();
                    companyExploredView.setError("Ocurrio un error al traer la información");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                companyExploredView.hideLoad();
                companyExploredView.setError("Ocurrio un error en la conexión al servidor");
            }
        });
    }

    private void populateDialog(List<RecycleItemEntity> recycleItemEntities)
    {
        companyExploredView.hideLoad();
        companyExploredView.getCategories(new ArrayList<RecycleItemEntity>(recycleItemEntities));
    }

    private void populate(List<CompanyEntity> babyEntities)
    {
        companyExploredView.hideLoad();
        companyExploredView.populate(new ArrayList<CompanyEntity>(babyEntities));
    }

    private void setMoreNext(List<CompanyEntity> babyEntities)
    {
        companyExploredView.hideLoad();
        companyExploredView.setMore(new ArrayList<CompanyEntity>(babyEntities));
    }

    @Override
    public void clickItemCompanyItem(CompanyEntity companyEntity) {
        companyExploredView.detailCompany(companyEntity);
    }
}