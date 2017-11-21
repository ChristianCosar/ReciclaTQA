package com.kodevian.reciclapp.views.commons;

import com.kodevian.reciclapp.model.AccessTokenEntity;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.views.commons.MasterView;

/**
 * Created by Leo on 05/02/2016.
 */
public interface UserView extends MasterView {

    public void ErroRegister(String msg);
    public void succesLogin(UserEntity userEntity);
    public void editUser(UserEntity userEntity);
    public void setmsg(String msg);

}
