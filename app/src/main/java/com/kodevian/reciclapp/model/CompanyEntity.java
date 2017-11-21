package com.kodevian.reciclapp.model;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by junior on 08/04/16.
 */
public class CompanyEntity implements Serializable {
    private String id;
    private String name;
    private String web;
    private Location location;
    private String address;
    private String telephone;
    private String monday_to_friday;
    private String saturday_sunday_holidays;
    private String celphone;
    private String phone;
    private String photo;
    private File image;
    private String c_type;
    private String id_categories;
    private ArrayList<RecycleItemEntity> categories;
    private ArrayList<RecycleItemTrack> categories_prices;
    private ArrayList<RecycleItemCategorieTrack> categories_companies;
    private boolean verify;
    private String ruc;
    private String code;

    public ArrayList<RecycleItemCategorieTrack> getCategories_companies() {
        return categories_companies;
    }

    public void setCategories_companies(ArrayList<RecycleItemCategorieTrack> categories_companies) {
        this.categories_companies = categories_companies;
    }

    public ArrayList<RecycleItemTrack> getCategories_prices() {
        return categories_prices;
    }

    public void setCategories_prices(ArrayList<RecycleItemTrack> categories_prices) {
        this.categories_prices = categories_prices;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public CompanyEntity() {
    }
    public CompanyEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public ArrayList<RecycleItemEntity> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<RecycleItemEntity> categories) {
        this.categories = categories;
    }

    public String getPhone() {
        return phone;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId_categories() {
        return id_categories;
    }

    public void setId_categories(String id_categories) {
        this.id_categories = id_categories;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMonday_to_friday() {
        return monday_to_friday;
    }

    public void setMonday_to_friday(String monday_to_friday) {
        this.monday_to_friday = monday_to_friday;
    }

    public String getSaturday_sunday_holidays() {
        return saturday_sunday_holidays;
    }

    public void setSaturday_sunday_holidays(String saturday_sunday_holidays) {
        this.saturday_sunday_holidays = saturday_sunday_holidays;
    }

    public String getCelphone() {
        return celphone;
    }

    public void setCelphone(String celphone) {
        this.celphone = celphone;
    }
}
