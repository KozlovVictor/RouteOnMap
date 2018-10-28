package ru.kozlov_victor.routeonmap.Data.Controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kozlov_victor.routeonmap.Data.DataRequest;

public class DataManager {
    private static final String BASE_URL = "https://test.www.estaxi.ru";

    DataRequest dataRequest;

    public void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataRequest = retrofit.create(DataRequest.class);
    }

    public DataRequest getDataRequest() {
        return dataRequest;
    }
}
