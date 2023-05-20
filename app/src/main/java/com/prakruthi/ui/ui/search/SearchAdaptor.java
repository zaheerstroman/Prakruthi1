package com.prakruthi.ui.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.ui.productPage.ProductPagerAdaptor;

import java.util.List;

public class SearchAdaptor extends RecyclerView.Adapter<SearchAdaptor.ViewHolder> {

    List<SearchModle> searchModles;

    public SearchAdaptor(List<SearchModle> searchModles)
    {
        this.searchModles = searchModles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(searchModles.get(position).getAttachments().get(0)).into(holder.Search_product_image);
        holder.Search_Product_name.setText(searchModles.get(position).getName());
        holder.Search_Product_Price.setText(searchModles.get(position).getCustomerPrice());
        holder.Search_Product_Price_real.setText(searchModles.get(position).getActualPrice());
        holder.Search_Product_Description.setText(searchModles.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return searchModles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView Search_product_image;
        TextView Search_Product_name , Search_Product_Price , Search_Product_Price_real , Search_Product_Description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Search_product_image = itemView.findViewById(R.id.Search_product_image);
            Search_Product_name = itemView.findViewById(R.id.Search_Product_name);
            Search_Product_Price = itemView.findViewById(R.id.Search_Product_Price);
            Search_Product_Price_real = itemView.findViewById(R.id.Search_Product_Price_real);
            Search_Product_Description = itemView.findViewById(R.id.Search_Product_Description);
        }
    }
}
