package com.example.hospitalfinder;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String name;
    private double latitude;
    private double lonitude;
    private int contextCatcher;
    private List<Hospital> hospitalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        name = getIntent().getStringExtra("name");
        latitude = getIntent().getDoubleExtra("latitude",0.0);
        lonitude = getIntent().getDoubleExtra("longitude",0.0);
        contextCatcher = getIntent().getIntExtra("context",0);
        if(contextCatcher==2) hospitalList = (List<Hospital>) getIntent().getSerializableExtra("hospitalList");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

            LatLng loc = new LatLng(latitude, lonitude);
            mMap.addMarker(new MarkerOptions().position(loc).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));



        if(contextCatcher==2)
        {
            for(int i=0;i<5;i++){
                Hospital hospital = hospitalList.get(i);
                LatLng hLoc = new LatLng(hospital.getLatitude(), hospital.getLongitude());
                mMap.addMarker(new MarkerOptions().position(hLoc).title(hospital.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hLoc, 12));
            }
        }
        else
            {
            mMap.setMyLocationEnabled(true);
        }
            mMap.getUiSettings().setZoomControlsEnabled(true);

    }
}
