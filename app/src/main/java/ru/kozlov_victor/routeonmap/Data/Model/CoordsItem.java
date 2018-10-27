package ru.kozlov_victor.routeonmap.Data.Model;

import com.google.gson.annotations.SerializedName;

public class CoordsItem {

    @SerializedName("lo")
    private double longtitude;

    @SerializedName("la")
    private double latitude;

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "la: " + this.latitude + " " + "lo: " + this.longtitude;
    }
}
