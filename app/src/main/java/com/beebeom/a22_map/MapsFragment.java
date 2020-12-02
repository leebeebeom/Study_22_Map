package com.beebeom.a22_map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    public static final String TAG = "맵";
    private GoogleMap mMap;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng myHome = new LatLng(37.53432988001302, 126.82435743818148);
            googleMap.addMarker(new MarkerOptions().position(myHome).title("울집"));
            //카메라 이동.
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myHome));
            //줌.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(17f));
            //마커에 정보 주기
            googleMap.setOnInfoWindowClickListener(marker -> {
                //마커를 클릭하면 전화가 걸림
                 Intent intent = new Intent(Intent.ACTION_DIAL);
                 intent.setData(Uri.parse("tel:01040382576"));
                 if(intent.resolveActivity(getContext().getPackageManager()) != null){
                     startActivity(intent);
                 }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    public void setCurrentLocation(LatLng currentLocation){
        //현재 위치 표시하기 메소드
        Log.d(TAG, "setCurrentLocation: "+currentLocation.toString());
        mMap.addMarker(new MarkerOptions().position(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17.0f));
    }
}