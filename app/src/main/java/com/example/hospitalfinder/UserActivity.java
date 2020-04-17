package com.example.hospitalfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static java.util.Collections.sort;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String role,userName;
    private DatabaseReference databaseReference,databaseReferenceHospital;
    private List<Hospital> hospitalList;
    private Location mLocation;
    private double latitude=1800,longitude=1800;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private LocationFinder locationFinder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationFinder = new LocationFinder(UserActivity.this);

        mAuth = FirebaseAuth.getInstance();
        resquestPermission();
        hospitalList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceHospital = (DatabaseReference) FirebaseDatabase.getInstance().getReference("HospitalInfo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                role = user.getRole();
                userName = user.getName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceHospital.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                hospitalList.clear();
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    Hospital hospital = data.getValue(Hospital.class);
                    hospitalList.add(hospital);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        MenuItem item = menu.findItem(R.id.addItem);
        if(role == "admin") item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.signOutId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent= new Intent(UserActivity.this,MainActivity.class);
            startActivity(intent);

        }
        else if(item.getItemId()==R.id.addItem)
        {
            Intent intent= new Intent(UserActivity.this,AddHospitalInfoActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);


    }
    public void showHospitalList(View view) {
        Intent intent = new Intent(UserActivity.this,HospitalListActivity.class);

        intent.putExtra("role",role);
        intent.putExtra("hospitalList", (Serializable) hospitalList);

        startActivity(intent);
    }

    public void nearByHospital(View view) {



        mLocation = locationFinder.getLocation();

        try {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
        }catch (Exception e) {
//            toastMessage(getResources().getString(R.string.reqLocShare));
            // toastMessage(e.getMessage());
        }



            final double lat = latitude;
            final double lon = longitude;
            List<Hospital> nearByList = hospitalList;


            Collections.sort(nearByList, new Comparator<Hospital>() {

                @Override
                public int compare(Hospital o1, Hospital o2) {
                    double a1, a2, a3, a4, x, y;
                    int compareRes = 0;

                    a1 = (o1.getLatitude() - lat);
                    a2 = (o2.getLatitude() - lat);
                    a3 = (o1.getLongitude() - lon);
                    a4 = (o2.getLongitude() - lon);
                    x = a1 * a1 + a3 * a3;
                    y = a2 * a2 + a4 * a4;

                    compareRes = Double.compare(x, y);
                    return compareRes;

                }
            });

            Intent intent = new Intent(UserActivity.this, MapsActivity.class);

            intent.putExtra("hospitalList", (Serializable) nearByList);
            intent.putExtra("name", userName);
            intent.putExtra("latitude", lat);
            intent.putExtra("longitude", lon);
            intent.putExtra("context", 2);

            startActivity(intent);


    }




    public void resquestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }
}
