package com.kodevian.reciclapp.views.commons;

import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.UserEntity;

import java.util.ArrayList;

/**
 * Created by junior on 31/03/16.
 */
public interface CategoriesRecycleView extends MasterView {
    public void selectItem(RecycleItemEntity recycleItemEntity);
    public void populate(ArrayList<RecycleItemEntity> arrayList);
    public void setMore(ArrayList<RecycleItemEntity> arrayList);
    public void ErroRegister(String msg);
    public void succesLogin(UserEntity userEntity);
    public void setmsg(String msg);
}
