package com.kodevian.reciclapp.views.commons;


import com.kodevian.reciclapp.model.UserEntity;

public interface LoginView extends MasterView {


    public void errorLogin(String error);
    public void succesLogin(UserEntity userEntity);
}
