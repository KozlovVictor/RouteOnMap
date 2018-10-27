package ru.kozlov_victor.routeonmap.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.kozlov_victor.routeonmap.Data.Model.ResponseData;

public interface GetData {

    @GET("/{dataSource}")
    Call<ResponseData> loadData(@Path("dataSource") String dataSource);
}
