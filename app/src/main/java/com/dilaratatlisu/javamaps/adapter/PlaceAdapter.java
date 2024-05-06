package com.dilaratatlisu.javamaps.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dilaratatlisu.javamaps.databinding.RecyclerRowBinding;
import com.dilaratatlisu.javamaps.model.Locations;
import com.dilaratatlisu.javamaps.view.MapsActivity;

import java.util.List;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {

    List<Locations> locationsList;

    public PlaceAdapter(List<Locations> locationsList) {
        this.locationsList = locationsList;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new PlaceHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.PlaceHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.recyclerRowBinding.recyclerViewTextView.setText(locationsList.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                intent.putExtra("info", "old");
                intent.putExtra("place", locationsList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return locationsList.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding recyclerRowBinding;
        public PlaceHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;

        }
    }

}
