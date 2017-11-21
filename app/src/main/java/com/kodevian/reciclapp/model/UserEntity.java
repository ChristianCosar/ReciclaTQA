package com.kodevian.reciclapp.model;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Leo on 12/01/2016.
 */
public class UserEntity implements Serializable {
    private String id;
    private String dni;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String celphone;
    private String birthdate;
    private String gender;
    private String id_categories;

    private ArrayList<RecycleItemEntity> recycleItemEntities;
    private ArrayList<Integer> categories;

    public ArrayList<RecycleItemEntity> getRecycleItemEntities() {
        return recycleItemEntities;
    }

    public void setRecycleItemEntities(ArrayList<RecycleItemEntity> recycleItemEntities) {
        this.recycleItemEntities = recycleItemEntities;
    }

    public String getCelphone() {
        return celphone;
    }

    public void setCelphone(String celphone) {
        this.celphone = celphone;
    }

    public ArrayList<Integer> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Integer> categories) {
        this.categories = categories;
    }

    public String getId_categories() {
        return id_categories;
    }

    public void setId_categories(String id_categories) {
        this.id_categories = id_categories;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
