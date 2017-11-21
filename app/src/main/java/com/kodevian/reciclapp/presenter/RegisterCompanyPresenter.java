package com.kodevian.reciclapp.presenter;

import android.content.Context;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.CompanyJuridicEntity;
import com.kodevian.reciclapp.model.CompanyReciclappEntity;
import com.kodevian.reciclapp.model.RecycleItemTrack;
import com.kodevian.reciclapp.model.TrackRecycleItemEntity;
import com.kodevian.reciclapp.model.UploadResponse;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterCompanyItem;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterDefinePrice;
import com.kodevian.reciclapp.request.CompanyRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.views.commons.MyCompaniesViews;
import com.kodevian.reciclapp.views.commons.RegisterCompanyView;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by junior on 08/04/16.
 */
public class RegisterCompanyPresenter implements CommunicatePresenterDefinePrice {

    RegisterCompanyView registerCompanyView;
    Context context;
    String next;

    public RegisterCompanyPresenter(Context context, RegisterCompanyView registerCompanyView) {
        this.registerCompanyView = registerCompanyView;
        this.context = context;
    }


    public void RegisterCompanyJuridic(final String token, final CompanyEntity companyEntity) {

        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        companyEntity.setCategories_prices(new ArrayList<RecycleItemTrack>());
        for (int i = 0; i < companyEntity.getCategories().size(); i++) {
            companyEntity.getCategories_prices().add(new RecycleItemTrack(companyEntity.getCategories().get(i).getId(),
                    companyEntity.getCategories().get(i).getPrice()));
        }
        companyEntity.setCelphone("+51" + companyEntity.getCelphone());
        Call<CompanyEntity> call = companyRequest.registerJuridicCompany("Token " + token, companyEntity);
        registerCompanyView.showLoad();
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                    if (companyEntity.getImage() != null) {
                        uploadPhotoJ(token, response.body(), companyEntity.getImage());
                    } else {
                        registerCompanyView.hideLoad();
                        registerCompanyView.registerJuridicCompany(response.body());
                    }

                } else {
                    registerCompanyView.hideLoad();
                    registerCompanyView.setError("Fallo el registro, puede que su  RUC ya esté registrado");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                registerCompanyView.hideLoad();
                registerCompanyView.setError("Ocurrió un error al registrar, por favor intente nuevamente");
            }
        });

    }

    public void RegisterCompanyReciclapp(final String token, final CompanyEntity companyEntity) {

        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        registerCompanyView.showLoad();
        companyEntity.setCategories_prices(new ArrayList<RecycleItemTrack>());
        for (int i = 0; i < companyEntity.getCategories().size(); i++) {
            companyEntity.getCategories_prices().add(new RecycleItemTrack(companyEntity.getCategories().get(i).getId(),
                    companyEntity.getCategories().get(i).getPrice()));
        }
        companyEntity.setCelphone("+51" + companyEntity.getCelphone());
        Call<CompanyEntity> call = companyRequest.registerReciclappCompany("Token " + token, companyEntity);

        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                    if (companyEntity.getImage() != null) {
                        uploadPhotoR(token, response.body(), companyEntity.getImage());
                    } else {
                        registerCompanyView.hideLoad();
                        registerCompanyView.registerReciclappCompany(response.body());
                    }


                } else {
                    registerCompanyView.hideLoad();
                    registerCompanyView.setError("Fallo el registro, por favor intente nuevamente");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                registerCompanyView.hideLoad();
                registerCompanyView.setError("Ocurrió un error al registrar, por favor intente nuevamente");
            }
        });

    }


    public void uploadPhotoR(String token, final CompanyEntity companyEntity, File file) {
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        RequestBody photo = RequestBody.create(MediaType.parse("application/photo"), file);
        RequestBody body = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("photo", file.getName(), photo)
                .build();


        Call<CompanyEntity> call = companyRequest.updateCompanyPhotos("Token " + token, companyEntity.getId(), body);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    registerCompanyView.hideLoad();
                    companyEntity.setPhoto(response.body().getPhoto());
                    registerCompanyView.registerReciclappCompany(companyEntity);
                } else {
                    registerCompanyView.hideLoad();
                    registerCompanyView.setError("Ocurrió un problema al guardar la imagen, verifique su empresa y edítela");
                    registerCompanyView.failureUploadImage(companyEntity);

                }

            }

            @Override
            public void onFailure(Throwable t) {
                registerCompanyView.hideLoad();
                registerCompanyView.setError("Ocurrió un problema al guardar la imagen, verifique su empresa y edítela");
                registerCompanyView.failureUploadImage(companyEntity);
            }
        });
    }

    public void uploadPhotoJ(String token, final CompanyEntity companyEntity, File file) {
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        RequestBody photo = RequestBody.create(MediaType.parse("application/photo"), file);
        RequestBody body = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("photo", file.getName(), photo)
                .build();


        Call<CompanyEntity> call = companyRequest.updateCompanyPhotos("Token " + token, companyEntity.getId(), body);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    registerCompanyView.hideLoad();
                    companyEntity.setPhoto(response.body().getPhoto());
                    registerCompanyView.registerJuridicCompany(companyEntity);
                } else {
                    registerCompanyView.hideLoad();
                    registerCompanyView.setError("Ocurrió un problema al guardar la imagen, verifique su empresa y edítela");
                    registerCompanyView.failureUploadImage(companyEntity);

                }

            }

            @Override
            public void onFailure(Throwable t) {
                registerCompanyView.hideLoad();
                registerCompanyView.setError("Ocurrió un problema al guardar la imagen, verifique su empresa y edítela");
                registerCompanyView.failureUploadImage(companyEntity);
            }
        });
    }

    @Override
    public void focusRecive() {
        registerCompanyView.focusEditText();
    }

    @Override
    public void focusReciveScroll() {
        registerCompanyView.focusEditTextScroll();
    }


}
