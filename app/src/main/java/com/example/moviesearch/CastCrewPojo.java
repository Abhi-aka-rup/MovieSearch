package com.example.moviesearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastCrewPojo {
    @SerializedName("cast_id")
    @Expose
    public Integer castId;

    @SerializedName("character")
    @Expose
    public String character;

    @SerializedName("credit_id")
    @Expose
    public String creditId;

    @SerializedName("gender")
    @Expose
    public Integer gender;

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("order")
    @Expose
    public Integer order;

    @SerializedName("profile_path")
    @Expose
    public String profilePath;

    public Integer getCastId() {
        return castId;
    }

    public String getCharacter() {
        return character;
    }

    public String getCreditId() {
        return creditId;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public String getProfilePath() {
        return profilePath;
    }
}