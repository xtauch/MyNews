package com.example.youpiman.mynews.Models.MostPopular;

/**
 * Created by Xavier TAUCH on 08/04/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopular {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<MostPopularResult> mMostPopularResults = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public List<MostPopularResult> getMostPopularResults() {
        return mMostPopularResults;
    }

    public void setMostPopularResults(List<MostPopularResult> mostPopularResults) {
        this.mMostPopularResults = mostPopularResults;
    }
}