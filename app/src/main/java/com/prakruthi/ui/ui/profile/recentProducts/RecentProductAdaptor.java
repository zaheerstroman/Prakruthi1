package com.prakruthi.ui.ui.profile.recentProducts;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.ui.productPage.ProductPage;

import java.util.ArrayList;

public class RecentProductAdaptor extends RecyclerView.Adapter<RecentProductAdaptor.ViewHolder> {

    ArrayList<RecentProductModel> recentProductModels;

    public RecentProductAdaptor(ArrayList<RecentProductModel> recentProductModels)
    {
        this.recentProductModels = recentProductModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create your view and inflate it from the layout XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_product_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentProductModel recentProductModel = recentProductModels.get(position);
        holder.ProductName.setText(recentProductModel.getName());
        Glide.with(holder.itemView.getContext())
                .load(recentProductModel.getAttachment())
                .placeholder(R.drawable.baseline_circle_24) // Optional placeholder image to show while loading the actual image.
                .into(holder.ProductPhoto);
        holder.itemView.setOnClickListener(v->{
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), ProductPage.class)
                    .putExtra("productId",String.valueOf(recentProductModel.getProduct_id())));
        });
    }

    @Override
    public int getItemCount() {
        return recentProductModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ProductPhoto;
        TextView ProductName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductPhoto   = itemView.findViewById(R.id.homepage_product_recycle_view_imageview);

            ProductName = itemView.findViewById(R.id.homepage_product_recycle_view_name_text);
        }
    }
}
