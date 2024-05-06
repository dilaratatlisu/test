package com.dilaratatlisu.javamaps.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.model.Places;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    ArrayList<Places> placesPopular;
    DecimalFormat decimalFormat;

    public PopularAdapter(ArrayList<Places> placesPopular) {
        this.placesPopular = placesPopular;
        decimalFormat = new DecimalFormat("###,###,###,###");
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholderpopular, parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {

        holder.textViewTitle.setText(placesPopular.get(position).getTitle());
        holder.location.setText(placesPopular.get(position).getLocation());

        int drawableResID = holder.itemView.getResources().getIdentifier(placesPopular.get(position).getImage(),
                "drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResID)
                .transform(new CenterCrop(), new GranularRoundedCorners(20,20,20,20))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return placesPopular.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, location;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            location = itemView.findViewById(R.id.location);
            image = itemView.findViewById(R.id.popularImageView);


        }
    }
}
