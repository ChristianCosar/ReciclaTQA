package com.kodevian.reciclapp.model;

/**
 * Created by junior on 08/04/16.
 */
public class CompanyJuridicEntity extends CompanyEntity {
    private String ruc;
    public CompanyJuridicEntity(){};
    public CompanyJuridicEntity(String name, String address) {
        super(name,address);
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
}
