package com.kodevian.reciclapp.model;

import java.io.Serializable;

/**
 * Created by junior on 03/05/16.
 */
public class RecycleItemTrack implements Serializable {

    private String id;
    private String price;


    public RecycleItemTrack(String id, String price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
