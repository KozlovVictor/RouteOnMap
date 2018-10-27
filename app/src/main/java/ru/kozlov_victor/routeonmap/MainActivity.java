package ru.kozlov_victor.routeonmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kozlov_victor.routeonmap.Data.Controller.DataManager;
import ru.kozlov_victor.routeonmap.Data.Model.CoordsItem;
import ru.kozlov_victor.routeonmap.Data.Model.ResponseData;


public class MainActivity extends AppCompatActivity {
    public static final String ROUTE = "route.txt";

    public Button loadPath;
    private List<CoordsItem> coordsItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPath = findViewById(R.id.load_path_button);

        coordsItemList = new ArrayList<>();
        final DataManager dataManager = new DataManager();
        dataManager.initRetrofit();

        loadPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataManager.getGetData().loadData(ROUTE).enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.body() != null) {
                            coordsItemList.addAll(response.body().getCoords());
                            //Log.d("response", "coordsItemList" + coordsItemList.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Ошибка загрузки данных.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
