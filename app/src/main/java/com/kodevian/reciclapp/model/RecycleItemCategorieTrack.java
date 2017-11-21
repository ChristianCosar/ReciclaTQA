package com.kodevian.reciclapp.model;

import java.io.Serializable;

/**
 * Created by junior on 03/05/16.
 */
public class RecycleItemCategorieTrack implements Serializable {
    private String id;
    private RecycleItemEntity category;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RecycleItemEntity getCategory() {
        return category;
    }

    public void setCategory(RecycleItemEntity category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
