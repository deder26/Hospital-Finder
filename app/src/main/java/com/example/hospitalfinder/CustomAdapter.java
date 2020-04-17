package com.example.hospitalfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Hospital>{

    private Context context;
    private List<Hospital> hospitalList;

    public CustomAdapter(Context context, List<Hospital> hospitalList) {
        super(context, R.layout.cutom_hospital_list_view, hospitalList);
        this.context = context;
        this.hospitalList = hospitalList;
    }

    class ViewHolder{
        TextView hospitalInfo;
        TextView alert;
        TextView phn;
        ImageView imageView;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();


            convertView = inflater.inflate(R.layout.cutom_hospital_list_view, null, true);
            holder.hospitalInfo = convertView.findViewById(R.id.hopitalNameID);
            holder.alert= convertView.findViewById(R.id.alertID);
            holder.phn= convertView.findViewById(R.id.phnID);
            holder.imageView= convertView.findViewById(R.id.mapData);


            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Hospital hospital = hospitalList.get(position);
        holder.hospitalInfo.setText(hospital.getName());
        holder.alert.setText(hospital.getAlert());
        holder.phn.setText(hospital.getPhone());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MapsActivity.class);
                intent.putExtra("name",hospital.getName());
                intent.putExtra("latitude",hospital.getLatitude());
                intent.putExtra("longitude",hospital.getLongitude());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
