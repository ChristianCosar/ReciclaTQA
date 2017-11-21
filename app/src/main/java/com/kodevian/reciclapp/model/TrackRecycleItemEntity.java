package com.kodevian.reciclapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by junior on 24/04/16.
 */
public class TrackRecycleItemEntity  implements Serializable{
    private ArrayList<RecycleItemEntity> categories;

    public ArrayList<RecycleItemEntity> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<RecycleItemEntity> categories) {
        this.categories = categories;
    }
}
