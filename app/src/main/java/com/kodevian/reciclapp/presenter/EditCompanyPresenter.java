package com.kodevian.reciclapp.presenter;

import android.content.Context;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.model.LocationTrack;
import com.kodevian.reciclapp.model.UploadResponse;
import com.kodevian.reciclapp.request.CompanyRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.views.commons.EditCompanyView;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by junior on 24/04/16.
 */
public class EditCompanyPresenter {

    EditCompanyView editCompanyView;
    Context context;
    SessionManager sessionManager;

    public EditCompanyPresenter(Context context, EditCompanyView editCompanyView) {
        this.editCompanyView = editCompanyView;
        this.context = context;
        sessionManager=new SessionManager(context);
    }


    public void uploadPhoto(String token, String id, File file){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();
        RequestBody photo = RequestBody.create(MediaType.parse("application/photo"), file);

        RequestBody body = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("photo",file.getName(), photo)
                .build();
        Call<CompanyEntity> call = companyRequest.updateCompanyPhotos("Token " + token, id, body);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    editCompanyView.hideLoad();
                   editCompanyView.uploadImage();
                }else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al guardar la imagen, verifique su empresa y edítela");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al guardar la imagen, verifique su empresa y edítela");

            }
        });
    }

    public void updateCellphone(String token, String id,String field){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();

        Call<CompanyEntity> call = companyRequest.updateCellphone("Token " + token, id, "+51" + field);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    editCompanyView.hideLoad();
                    editCompanyView.updateCompany(response.body());
                }else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");

            }
        });
    }
    public void update_L_V(String token, String id,String field){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();

        Call<CompanyEntity> call = companyRequest.update_L_V("Token " + token, id, field);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    editCompanyView.hideLoad();
                    editCompanyView.updateCompany(response.body());
                }else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");

            }
        });
    }
    public void update_S_D(String token, String id,String field){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();

        Call<CompanyEntity> call = companyRequest.update_S_D("Token " + token, id, field);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    editCompanyView.hideLoad();
                    editCompanyView.updateCompany(response.body());
                }else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");

            }
        });
    }
    public void updateAddress(String token, String id,String field){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();

        Call<CompanyEntity> call = companyRequest.updateAddress("Token " + token, id, field);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    editCompanyView.hideLoad();
                    editCompanyView.updateCompany(response.body());
                }else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");

            }
        });
    }
    public void updateEmail(String token, String id,String field){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();

        Call<CompanyEntity> call = companyRequest.updateEmail("Token " + token, id, field);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    editCompanyView.hideLoad();
                    editCompanyView.updateCompany(response.body());
                }else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");

            }
        });
    }

    public void updatePhone(String token, String id,String field){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();

        Call<CompanyEntity> call = companyRequest.updatePhone("Token " + token, id, field);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    editCompanyView.hideLoad();
                    editCompanyView.updateCompany(response.body());
                }else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");

            }
        });
    }

    public void updateLocation(String token, String id,Location field){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();
        LocationTrack locationTrack= new LocationTrack(field);
        Call<CompanyEntity> call = companyRequest.updateLocation("Token " + token, id, locationTrack);
        call.enqueue(new Callback<CompanyEntity>() {
            @Override
            public void onResponse(Response<CompanyEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    editCompanyView.hideLoad();
                    editCompanyView.updateCompany(response.body());
                } else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al actualizar, inténtelo nuevamente");

            }
        });
    }

    public void deleteCompany(String token, String id){
        CompanyRequest companyRequest =
                ServiceGeneratorSimple.createService(CompanyRequest.class);
        editCompanyView.showLoad();
        Call<Void> call = companyRequest.deleteCompany("Token " + token, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    editCompanyView.hideLoad();
                    editCompanyView.deleteCompany();

                } else {
                    editCompanyView.hideLoad();
                    editCompanyView.setError("Ocurrió un problema al eliminar, inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                editCompanyView.hideLoad();
                editCompanyView.setError("Ocurrió un problema al eliminar, inténtelo nuevamente");

            }
        });
    }
}
