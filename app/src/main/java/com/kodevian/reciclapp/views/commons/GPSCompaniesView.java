package com.kodevian.reciclapp.views.commons;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;

import java.util.ArrayList;

/**
 * Created by junior on 10/05/16.
 */
public interface GPSCompaniesView {
    public void populate(ArrayList<CompanyEntity> companyEntities);
    public void setError(String error);
}
