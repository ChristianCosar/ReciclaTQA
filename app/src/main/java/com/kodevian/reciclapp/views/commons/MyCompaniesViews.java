package com.kodevian.reciclapp.views.commons;

import com.kodevian.reciclapp.model.CompanyEntity;

import java.util.ArrayList;

/**
 * Created by junior on 08/04/16.
 */
public interface MyCompaniesViews extends MasterView {
    public void populate(ArrayList<CompanyEntity> arrayList);
    public void setMore(ArrayList<CompanyEntity> arrayList);
    public void detailCompany(CompanyEntity companyEntity);
}
