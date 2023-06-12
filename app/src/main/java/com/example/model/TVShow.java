package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class TVShow {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("permalink")
    private String permalink;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("end_date")
    private Boolean end_date;
    @SerializedName("country")
    private String country;
    @SerializedName("network")
    private String network;
    @SerializedName("status")
    private String status;
    @SerializedName("image_thumbnail_path")
    private String image_thumbnail_path;

    public int getId() {
        return id;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getName() {
        return name;
    }

    public String getStart_date() {
        return start_date;
    }

    public Boolean getEnd_date() {
        return end_date;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }
}
