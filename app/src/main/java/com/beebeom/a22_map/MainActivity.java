package com.beebeom.a22_map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PERMISSIONS = 1000;
    public static final String TAG = "맵";
    private FusedLocationProviderClient mClient;
    private MapsFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapFragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.map_frag);

        //위치정보를 얻기 위해선 FusedLocationProviderClient 가 필요함
        //그래들 추갸 안해주면 못씀.
        mClient = LocationServices.getFusedLocationProviderClient(this);

        findViewById(R.id.btn_getLocation).setOnClickListener(v -> {
            //위치정보 권한 체크
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //체크해야할 퍼미션
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                //권한 체크 다이얼로그 띄워줌.
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS);
            } else {
                //권한 있을시 실행
                Log.d(TAG, "onRequestPermissionsResult: 클릭");
                mClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMapFragment.setCurrentLocation(currentLocation);
                    }
                });
            }
        });
    }

    //퍼미션 체크 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            //권한체크 안됐을 시
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //거부 헀을 때처리
                Toast.makeText(this, "권한 체크 거부됨", Toast.LENGTH_SHORT).show();
            } else {
                //동의 했을 시 처리
                Log.d(TAG, "onRequestPermissionsResult: 클릭");
                mClient.getLastLocation().addOnSuccessListener(this, location -> {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMapFragment.setCurrentLocation(currentLocation);

                });
            }

        }

    }
}