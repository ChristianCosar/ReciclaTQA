package com.kodevian.reciclapp.model;

import java.io.Serializable;

/**
 * Created by junior on 11/05/16.
 */
public class RecycleItemTrackCategoryAdd implements Serializable {
    private String price;
    private String id;
    private String category;
    private String category_name;
    private String category_img;


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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_img() {
        return category_img;
    }

    public void setCategory_img(String category_img) {
        this.category_img = category_img;
    }
}
