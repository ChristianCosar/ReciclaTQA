package com.kodevian.reciclapp.request.tracks;
import com.kodevian.reciclapp.model.RecycleItemEntity;

import java.util.List;

/**
 * Created by Leo on 01/02/2016.
 */
public class TrackItemRecycleHolder {

    public String next;
    public String previous;
    public List<RecycleItemEntity> results;

    public TrackItemRecycleHolder(String next, String previous) {
        this.next = next;
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<RecycleItemEntity> getResults() {
        return results;
    }

    public void setResults(List<RecycleItemEntity> results) {
        this.results = results;
    }
}
