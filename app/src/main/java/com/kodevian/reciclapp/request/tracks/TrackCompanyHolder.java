package com.kodevian.reciclapp.request.tracks;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.RecycleItemEntity;

import java.util.List;

/**
 * Created by Leo on 01/02/2016.
 */
public class TrackCompanyHolder {

    public String next;
    public String previous;
    public List<CompanyEntity> results;

    public TrackCompanyHolder(String next, String previous) {
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

    public List<CompanyEntity> getResults() {
        return results;
    }

    public void setResults(List<CompanyEntity> results) {
        this.results = results;
    }
}
