package com.example.hospitalfinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterRecyclerView extends RecyclerView.Adapter<CustomAdapterRecyclerView.hospitalHolder>{

    private Context context;
    private List<Hospital> hospitalList;

    public CustomAdapterRecyclerView(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public hospitalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cutom_hospital_list_view,parent,false);

        return new hospitalHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull hospitalHolder holder, int position) {

        final Hospital hospital = hospitalList.get(position);
        holder.name.setText(hospital.getName());
        holder.alert.setText(hospital.getAlert());
        holder.phn.setText(hospital.getPhone());
        holder.loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("name",hospital.getName());
                intent.putExtra("latitude",hospital.getLatitude());
                intent.putExtra("longitude",hospital.getLongitude());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public class hospitalHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView phn;
        TextView alert;
        ImageView loc;

        public hospitalHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hopitalNameID);
            alert= itemView.findViewById(R.id.alertID);
            phn= itemView.findViewById(R.id.phnID);
            loc= itemView.findViewById(R.id.mapData);
        }
    }
}
