package ru.kozlov_victor.routeonmap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kozlov_victor.routeonmap.Data.Controller.DataManager;
import ru.kozlov_victor.routeonmap.Data.Model.CoordsItem;
import ru.kozlov_victor.routeonmap.Data.Model.ResponseData;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String ROUTE_PATH = "route.txt";
    public static final int ROUTE_LINE_WIDTH = 5;
    public static final int BOUNDS_PADDING = 0;
    public static final String DATA_LOADING_ERROR = "Ошибка загрузки данных.";

    public Button loadPathButton;
    private List<LatLng> latLngList;
    private List<CoordsItem> coordsItemList;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loadPathButton = findViewById(R.id.load_path_button);

        coordsItemList = new ArrayList<>();
        latLngList = new ArrayList<>();

        final DataManager dataManager = new DataManager();
        dataManager.initRetrofit();

        loadPathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataManager.getDataRequest().loadData(ROUTE_PATH).enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.body() != null) {
                            coordsItemList.addAll(response.body().getCoords());
                        }
                        for (int i = 0; i < coordsItemList.size(); i++) {
                            latLngList.add(new LatLng(coordsItemList.get(i).getLatitude(), coordsItemList.get(i).getLongtitude()));
                        }
                        drawRouteOnMap();
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        Toast.makeText(MainActivity.this, DATA_LOADING_ERROR, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void drawRouteOnMap() {
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(latLngList)
                .color(Color.YELLOW)
                .width(ROUTE_LINE_WIDTH);
        mMap.addPolyline(polylineOptions);
        LatLngBounds routeBounds = calculateRouteBounds(latLngList);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(routeBounds, BOUNDS_PADDING));
    }

    @NonNull
    private LatLngBounds calculateRouteBounds(List<LatLng> latLngList) {
        double minLat, maxLat, minLon, maxLon;
        minLat = maxLat = latLngList.get(0).latitude;
        minLon = maxLon = latLngList.get(0).longitude;

        for (int i = 1; i < latLngList.size(); i++) {
            double currentLat = latLngList.get(i).latitude;
            double currentLon = latLngList.get(i).longitude;

            if (currentLat < minLat) minLat = currentLat;
            else if (currentLat > maxLat) maxLat = currentLat;
            if (currentLon < minLon) minLon = currentLon;
            else if (currentLon > maxLon) maxLon = currentLon;
        }
        return new LatLngBounds(new LatLng(minLat, minLon), new LatLng(maxLat, maxLon));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
