package com.example.hospitalfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HospitalListActivity extends AppCompatActivity {


    private Button btn;
    private ImageView imageView;
    private User user;
    private List<Hospital> hospitalList;
    private CustomAdapter customAdapter;
    private String role;
    private ListView hopitalLV;
    private RecyclerView recyclerView;
    private CustomAdapterRecyclerView customAdapterRecyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list_view);

        recyclerView = (RecyclerView) findViewById(R.id.recylerID);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());

        //hospitalList = getIntent().getParcelableExtra("hospitalList");
        hospitalList = new ArrayList<>();
        hospitalList = (ArrayList<Hospital>) getIntent().getSerializableExtra("hospitalList");
       // customAdapter = new CustomAdapter(HospitalListActivity.this,hospitalList);
        customAdapterRecyclerView = new CustomAdapterRecyclerView(HospitalListActivity.this,hospitalList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //hopitalLV = (ListView) findViewById(R.id.hospitalLVId);
       // role = getIntent().getStringExtra("role");
        recyclerView.setAdapter(customAdapterRecyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        MenuItem item = menu.findItem(R.id.addItem);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.signOutId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent= new Intent(HospitalListActivity.this,MainActivity.class);
            startActivity(intent);

        }
        else if(item.getItemId()==R.id.addItem)
        {
            Intent intent= new Intent(HospitalListActivity.this,AddHospitalInfoActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);


    }



    public void AddInfo(View view) {

        Intent intent = new Intent(HospitalListActivity.this,AddHospitalInfoActivity.class);
        startActivity(intent);
    }

    public void ShopMap(String name,double latitude,double longitude) {

        Intent intent = new Intent(HospitalListActivity.this,MapsActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        intent.putExtra("context",1);

        startActivity(intent);

    }
}
