package com.kodevian.reciclapp.views.commons;

import com.kodevian.reciclapp.model.CompanyEntity;

/**
 * Created by junior on 24/04/16.
 */
public interface EditCompanyView extends MasterView {
    public void editEmail(String email);
    public void editName(String name);
    public void editAddress(String Address);
    public void editCellphone(String cellphone);
    public void editPhone(String phone);
    public void atention_monday_friday(String atention_monday_friday);
    public void atention_holiday(String atention_holiday);
    public void editRuc(String web);
    public void uploadImage();
    public void updateCompany(CompanyEntity companyEntity);
    public void deleteCompany();

}
