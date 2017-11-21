package com.kodevian.reciclapp.views.commons;

import android.support.v7.widget.RecyclerView;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.views.fragments.dialog.SearchDialog;

import java.util.ArrayList;

/**
 * Created by junior on 25/04/16.
 */
public interface CompanyExploredView extends MasterView {

    public void populate(ArrayList<CompanyEntity> arrayList);
    public void setMore(ArrayList<CompanyEntity> arrayList);
    public void detailCompany(CompanyEntity companyEntity);
    public void getCategories(ArrayList<RecycleItemEntity> arrayList);
    public void searchCompanies(String idCategory,boolean reciclapp, boolean juridic, boolean verify,Location locationGPS);
    public void searchCompanies(String idCategory,boolean reciclapp, boolean juridic, boolean verify);
    public void foundCompanies();
    public void populateDialogSearch(RecyclerView recyclerView);
    public void requiredPermissonLocation();
}
