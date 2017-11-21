package com.kodevian.reciclapp.presenter;


import android.content.Context;

import com.kodevian.reciclapp.model.AccessTokenEntity;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.request.UserRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.views.commons.UserView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Leo on 05/02/2016.
 */
public class UserPresenter {
    UserView userView;
    SessionManager sessionManager;

    public UserPresenter(Context context,UserView userView) {
        this.userView = userView;
        this.sessionManager = new SessionManager(context);

    }
    
    public void RegisterUser(UserEntity userEntity){
        UserRequest userRequest =
                ServiceGeneratorSimple.createService(UserRequest.class);
        if(!userEntity.getCelphone().equals("")){
            userEntity.setCelphone("+51"+userEntity.getCelphone());
        }
        Call<AccessTokenEntity> call = userRequest.registerUser( userEntity);
        userView.showLoad();
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Response<AccessTokenEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    userView.setmsg("Registro exitoso");
                    getPerfil(response.body());
                } else {
                    userView.hideLoad();
                    userView.ErroRegister("Fallo el registro, puede que su  Email ya esté registrado");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                userView.hideLoad();
                userView.ErroRegister("Ocurrió un error al registrar, por favor intente nuevamente");
            }
        });
    }
    public void EditUser(String Token,String id,UserEntity userEntity){


        UserRequest userRequest =
                ServiceGeneratorSimple.createService(UserRequest.class);

        Call<UserEntity> call = userRequest.editUser("Token "+Token,
                userEntity.getFirst_name(), userEntity.getLast_name(),
                userEntity.getGender(), "+51" + userEntity.getCelphone(),
                userEntity.getBirthdate(), id );
        userView.showLoad();
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Response<UserEntity> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    userView.hideLoad();
                    userView.setmsg("Edición exitosa");
                    userView.editUser(response.body());
                } else {
                    userView.hideLoad();
                    userView.ErroRegister("Falló la edición, intente nuevamente");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                userView.hideLoad();
                userView.ErroRegister("Ocurrió un error al editar, por favor intente nuevamente");
            }
        });
    }
    private void getPerfil(final AccessTokenEntity tokenEntity){

        UserRequest userRequest =
                ServiceGeneratorSimple.createService(UserRequest.class);
        userView.setmsg("Abriendo sesión...");
        Call<UserEntity> call = userRequest.getUser("Token " + tokenEntity.getAccessToken());
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Response<UserEntity> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    openSession(tokenEntity.getAccessToken(), response.body());
                }else {
                    userView.hideLoad();
                    userView.setError("Ocurrió un error al cargar su perfil, intente loguearse en la pantalla principal");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                userView.hideLoad();
                userView.setError("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    private void openSession(String token, UserEntity userEntity){
        sessionManager.openSession(token,userEntity);
        userView.hideLoad();
        userView.succesLogin(userEntity);
    }




}
