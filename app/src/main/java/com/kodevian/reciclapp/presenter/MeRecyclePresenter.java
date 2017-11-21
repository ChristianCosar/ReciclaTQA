package com.kodevian.reciclapp.presenter;

import android.content.Context;

import com.kodevian.reciclapp.model.AccessTokenEntity;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterRecycleItem;
import com.kodevian.reciclapp.request.CategoriesRecycleRequest;
import com.kodevian.reciclapp.request.UserRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.request.tracks.TrackItemRecycleHolder;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.views.commons.CategoriesRecycleView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by junior on 31/03/16.
 */
public class MeRecyclePresenter implements CommunicatePresenterRecycleItem {

    CategoriesRecycleView categoriesRecycleView;
    String next;
    Context context;
    SessionManager sessionManager;

    public MeRecyclePresenter(CategoriesRecycleView categoriesRecycleView,Context context) {
        this.categoriesRecycleView = categoriesRecycleView;
        this.context = context;
        this.sessionManager = new SessionManager(context);

    }

    public void getCategories(){
        final CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        categoriesRecycleView.showLoad();
        Call<TrackItemRecycleHolder> call = categoriesRecycleRequest.getCategories();
        call.enqueue(new Callback<TrackItemRecycleHolder>() {
            @Override
            public void onResponse(Response<TrackItemRecycleHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackItemRecycleHolder trackDiseaseHolder= response.body();
                    next = trackDiseaseHolder.getNext();
                    populate(trackDiseaseHolder.getResults());
                } else {
                    categoriesRecycleView.hideLoad();
                    categoriesRecycleView.setError("Ocurrio un error al traer la información");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                categoriesRecycleView.hideLoad();
                categoriesRecycleView.setError("Ocurrio un error en la conexión al servidor");
            }
        });
    }

    public void RegisterUser(UserEntity userEntity){
        UserRequest userRequest =
                ServiceGeneratorSimple.createService(UserRequest.class);
        if(!userEntity.getCelphone().equals("")){
            userEntity.setCelphone("+51"+userEntity.getCelphone());
        }
        Call<AccessTokenEntity> call = userRequest.registerUser( userEntity);
        categoriesRecycleView.showLoad();
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Response<AccessTokenEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    categoriesRecycleView.setmsg("Registro exitoso");
                    getPerfil(response.body());
                } else {
                    categoriesRecycleView.hideLoad();
                    categoriesRecycleView.ErroRegister("Fallo el registro, puede que su  Email ya esté registrado");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                categoriesRecycleView.hideLoad();
                categoriesRecycleView.ErroRegister("Ocurrió un error al registrar, por favor intente nuevamente");
            }
        });
    }
    private void populate(List<RecycleItemEntity> recycleItemEntities)
    {
        categoriesRecycleView.hideLoad();
        categoriesRecycleView.populate(new ArrayList<RecycleItemEntity>(recycleItemEntities));
    }

    private void setMoreNext(List<RecycleItemEntity> recycleItemEntities)
    {
        categoriesRecycleView.hideLoad();
        categoriesRecycleView.setMore(new ArrayList<RecycleItemEntity>(recycleItemEntities));
    }
    @Override
    public void clickItemRecycle(RecycleItemEntity recycleItemEntity) {
        categoriesRecycleView.selectItem(recycleItemEntity);
    }

    private void getPerfil(final AccessTokenEntity tokenEntity){

        UserRequest userRequest =
                ServiceGeneratorSimple.createService(UserRequest.class);
        categoriesRecycleView.setmsg("Abriendo sesión...");
        Call<UserEntity> call = userRequest.getUser("Token " + tokenEntity.getAccessToken());
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Response<UserEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    openSession(tokenEntity.getAccessToken(), response.body());
                } else {
                    categoriesRecycleView.hideLoad();
                    categoriesRecycleView.setError("Ocurrió un error al cargar su perfil, intente loguearse en la pantalla principal");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                categoriesRecycleView.hideLoad();
                categoriesRecycleView.setError("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    private void openSession(String token, UserEntity userEntity){
        sessionManager.openSession(token,userEntity);
        categoriesRecycleView.hideLoad();
        categoriesRecycleView.succesLogin(userEntity);
    }
}
