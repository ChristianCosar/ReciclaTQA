package com.kodevian.reciclapp.views.commons;


import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.CompanyJuridicEntity;
import com.kodevian.reciclapp.model.CompanyReciclappEntity;
import com.kodevian.reciclapp.model.UserEntity;

public interface RegisterCompanyView extends MasterView {


    public void registerJuridicCompany(CompanyEntity companyJuridicEntity);
    public void registerReciclappCompany(CompanyEntity companyReciclappEntity);
    public void focusEditText();
    public void focusEditTextScroll();
    public void failureUploadImage(CompanyEntity companyEntity);

}
