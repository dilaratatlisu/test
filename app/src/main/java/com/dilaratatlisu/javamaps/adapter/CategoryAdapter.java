package com.dilaratatlisu.javamaps.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> categoryItems;

    public CategoryAdapter(ArrayList<Category> categoryItems) {
        this.categoryItems = categoryItems;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewolder_category, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.title.setText(categoryItems.get(position).getTitle());
        int drawableResourcesId = holder.itemView.getResources().getIdentifier(categoryItems.get(position).getImageUrl(), "drawable",
                holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourcesId)
                .into(holder.imageCategory);
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewCategory);
            imageCategory = itemView.findViewById(R.id.imageViewCat);
        }
    }
}
