package ru.kozlov_victor.routeonmap.Data.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {

    @SerializedName("coords")
    private List<CoordsItem> coords;

    public List<CoordsItem> getCoords() {
        return coords;
    }
}
