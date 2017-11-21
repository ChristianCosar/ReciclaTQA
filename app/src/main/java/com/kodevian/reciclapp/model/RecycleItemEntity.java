package com.kodevian.reciclapp.model;

import java.io.Serializable;

/**
 * Created by junior on 30/03/16.
 */
public class RecycleItemEntity implements Serializable {

    private String id;
    private String name;
    private String img;
    private String price;


    public RecycleItemEntity(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public RecycleItemEntity( String name, String img,String id) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
