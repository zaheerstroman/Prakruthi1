package com.prakruthi.ui.ui.profile.recentProducts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakruthi.R;

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

    }

    @Override
    public int getItemCount() {
        return recentProductModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
