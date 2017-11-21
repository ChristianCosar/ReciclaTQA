package com.kodevian.reciclapp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterCompanyItem;
import com.kodevian.reciclapp.request.CompanyRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.request.tracks.TrackCompanyHolder;
import com.kodevian.reciclapp.views.commons.CompanyExploredView;
import com.kodevian.reciclapp.views.commons.MyCompaniesViews;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by junior on 08/04/16.
 */
public class MyCompaniesPresenter implements CommunicatePresenterCompanyItem {

    MyCompaniesViews myCompaniesViews;
    CompanyExploredView companyExploredView;
    Context context;
    String next;

    public MyCompaniesPresenter(Context context,MyCompaniesViews myCompaniesViews) {
        this.myCompaniesViews = myCompaniesViews;
        this.context = context;
    }


    public void getCompanies(String token){
        final CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        myCompaniesViews.showLoad();
        Call<TrackCompanyHolder> call = companyRequest.getMyCompanies("Token " + token);

        call.enqueue(new Callback<TrackCompanyHolder>() {
            @Override
            public void onResponse(Response<TrackCompanyHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackCompanyHolder trackCompanyHolder= response.body();
                    next = trackCompanyHolder.getNext();
                    populate(trackCompanyHolder.getResults());
                } else {

                    myCompaniesViews.setError("Ocurrio un error al traer la información.");
                    myCompaniesViews.hideLoad();
                }
            }

            @Override public void onFailure(Throwable t) {

                myCompaniesViews.setError("Ocurrio un error al traer la información");
                myCompaniesViews.hideLoad();
            }
        });
    }

    public void getMore(String token){
        if(next!=null){
            if(!TextUtils.isEmpty(next)){
                final CompanyRequest companyRequest =
                        ServiceGeneratorSimple.createService(CompanyRequest.class);
                Call<TrackCompanyHolder> call = companyRequest.getMoreMyCOmpanies("Token "+token,next);
                call.enqueue(new Callback<TrackCompanyHolder>() {

                    @Override
                    public void onResponse(Response<TrackCompanyHolder> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            TrackCompanyHolder trackCompanyHolder= response.body();
                            next = trackCompanyHolder.getNext();
                            setMoreNext(trackCompanyHolder.getResults());
                        } else {
                            myCompaniesViews.hideLoad();
                            myCompaniesViews.setError("Ocurrio un error al traer la información");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        myCompaniesViews.hideLoad();
                        myCompaniesViews.setError("Ocurrio un error al traer la información");
                    }
                });
            }else {
                myCompaniesViews.hideLoad();
            }
        }else{
            myCompaniesViews.hideLoad();
        }




    }

    private void populate(List<CompanyEntity> babyEntities)
    {
        myCompaniesViews.hideLoad();
        myCompaniesViews.populate(new ArrayList<CompanyEntity>(babyEntities));
    }

    private void setMoreNext(List<CompanyEntity> babyEntities)
    {
        myCompaniesViews.hideLoad();
        myCompaniesViews.setMore(new ArrayList<CompanyEntity>(babyEntities));
    }



    @Override
    public void clickItemCompanyItem(CompanyEntity companyEntity) {
        myCompaniesViews.detailCompany(companyEntity);
    }
}
