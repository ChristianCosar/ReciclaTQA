package com.kodevian.reciclapp.request.tracks;

import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.model.RecycleItemEntity;

import java.util.List;

/**
 * Created by junior on 10/05/16.
 */
public class TrackLocationHolder {

    public String next;
    public String previous;
    public List<Location> results;

    public TrackLocationHolder(String next, String previous) {
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

    public List<Location> getResults() {
        return results;
    }

    public void setResults(List<Location> results) {
        this.results = results;
    }
}
