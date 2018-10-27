package ru.kozlov_victor.routeonmap.Data.Controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kozlov_victor.routeonmap.Data.GetData;

public class DataManager {
    private static final String BASE_URL = "https://test.www.estaxi.ru";

    GetData getData;

    public void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getData = retrofit.create(GetData.class);
    }

    public GetData getGetData() {
        return getData;
    }
}
