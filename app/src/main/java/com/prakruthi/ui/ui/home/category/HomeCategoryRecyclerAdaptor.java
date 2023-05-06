package com.prakruthi.ui.ui.home.category;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeCategoryRecyclerAdaptor extends RecyclerView.Adapter<HomeCategoryRecyclerAdaptor.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
