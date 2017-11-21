package com.kodevian.reciclapp.presenter;

import android.content.Context;

import com.facebook.AccessToken;
import com.kodevian.reciclapp.model.AccessTokenEntity;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.request.LoginRequest;
import com.kodevian.reciclapp.request.UserRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.views.commons.LoginView;


import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by manu on 11/12/15.
 */
public class LoginPresenter {

    LoginView loginView;
    Context context;
    SessionManager sessionManager;


    public LoginPresenter(Context context, LoginView loginView){
        this.loginView = loginView;
        this.context = context;
        this.sessionManager = new SessionManager(context);
    }

    public void loginUser(String username, String password){
        LoginRequest loginService =
                ServiceGeneratorSimple.createService(LoginRequest.class);
        Call<AccessTokenEntity> call = loginService.basicLogin(username, password);
        loginView.showLoad();
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Response<AccessTokenEntity> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    getPerfil(response.body());
                }else {
                    loginView.hideLoad();
                    loginView.errorLogin("Email o contraseña incorrectos");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                loginView.hideLoad();
                loginView.errorLogin("Ocurrió un error al tratar de ingresar, por favor intente nuevamente");
            }
        });

    }

    public void loginUserFacebook(String token){
        LoginRequest loginService =
                ServiceGeneratorSimple.createService(LoginRequest.class);

        Call<AccessTokenEntity> call = loginService.loginFacebook(token);
        loginView.showLoad();
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Response<AccessTokenEntity> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    getPerfil(response.body());
                }else {
                    loginView.hideLoad();
                    AccessToken.setCurrentAccessToken(null);
                    loginView.errorLogin("Login fallido, puede que el correo vinculado a su " +
                            "facebook ya este registrado ");

                }
            }

            @Override
            public void onFailure(Throwable t) {
                loginView.hideLoad();
                AccessToken.setCurrentAccessToken(null);
                loginView.errorLogin("Ocurrió un error al entrar, por favor intente nuevamente");
            }
        });

    }



    private void getPerfil(final AccessTokenEntity tokenEntity){

        UserRequest userRequest =
                ServiceGeneratorSimple.createService(UserRequest.class);
        Call<UserEntity> call = userRequest.getUser("Token " + tokenEntity.getAccessToken());
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Response<UserEntity> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    openSession(tokenEntity.getAccessToken(), response.body());
                }else {
                    loginView.hideLoad();
                    loginView.errorLogin("Ocurrió un error al cargar su perfil");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                loginView.hideLoad();
                loginView.errorLogin("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    private void openSession(String token, UserEntity userEntity){
        sessionManager.openSession(token,userEntity);
        loginView.hideLoad();
        loginView.succesLogin(userEntity);
    }




}
