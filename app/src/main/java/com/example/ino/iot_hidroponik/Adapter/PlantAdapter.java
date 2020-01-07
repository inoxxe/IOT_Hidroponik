package com.example.ino.iot_hidroponik.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ino.iot_hidroponik.Model.Plant;
import com.example.ino.iot_hidroponik.R;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private Context context;

    List<Plant> plantList;

    public PlantAdapter(List<Plant> plantList, Context context){
        this.plantList = plantList;
        this.context = context;
    }


    class PlantViewHolder extends RecyclerView.ViewHolder {
        //Views
        public TextView textViewMenu,textViewid;


        //Initializing Views
        public PlantViewHolder(View itemView) {
            super(itemView);
            textViewMenu = (TextView) itemView.findViewById(R.id.planttext);
            textViewid = itemView.findViewById(R.id.plantid);

        }
    }

    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant,parent,false);
        PlantViewHolder viewHolder = new PlantViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlantViewHolder holder, int postition) {
        Plant plant = plantList.get(postition);
        holder.textViewMenu.setText(plantList.get(postition).getName());
        holder.textViewid.setText(plantList.get(postition).getName());
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }
}

