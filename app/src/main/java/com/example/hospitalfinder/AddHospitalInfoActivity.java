package com.example.hospitalfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddHospitalInfoActivity extends AppCompatActivity {

    private EditText name, phn, latitude,longitude,alert;
    private DatabaseReference databaseReference;
    private Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital_info);
        name = (EditText) findViewById(R.id.addName);

        phn = (EditText) findViewById(R.id.addPhone);
        latitude = (EditText) findViewById(R.id.addLatitude);
        longitude = (EditText) findViewById(R.id.addLongitude);
        alert = (EditText) findViewById(R.id.addAlert);

        databaseReference = FirebaseDatabase.getInstance().getReference("HospitalInfo");


    }

    public void AddHospitalInfo(View view) {
        String hName,hDescription, hPhn, hLatitude,hLongitude,hAlert;
        hName = name.getText().toString();

        hPhn = phn.getText().toString();
        hLatitude = latitude.getText().toString();
        hLongitude = longitude.getText().toString();
        hAlert = alert.getText().toString();

        if(hName.isEmpty()) name.setError("Can't Be Empty");
        else if(hPhn.isEmpty()) phn.setError("Can't Be Empty");
        else if(hLatitude.isEmpty()) latitude.setError("Can't Be Empty");
        else if(hLongitude.isEmpty()) longitude.setError("Can't Be Empty");
        else {

            String key = databaseReference.push().getKey();

            hospital = new Hospital(hName, hPhn, hAlert, Double.parseDouble(hLatitude), Double.parseDouble(hLongitude));
            databaseReference.child(key).setValue(hospital);

            Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show();

            name.setText("");
            alert.setText("");
            longitude.setText("");
            latitude.setText("");
            phn.setText("");

        }

    }
}
