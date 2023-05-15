package com.prakruthi.ui.ui.home.products;

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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeProductAdaptor extends RecyclerView.Adapter<HomeProductAdaptor.ViewHolder> {

    private final List<HomeProductModel> homeProductModelList;

    public HomeProductAdaptor(List<HomeProductModel> homeProductModelList) {
        this.homeProductModelList = homeProductModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_product_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeProductModel homeProductModel = homeProductModelList.get(position);
        holder.productTextView.setText(homeProductModel.getName());
        Glide.with(holder.itemView.getContext())
                .load(homeProductModel.getAttachment())
                .placeholder(R.drawable.baseline_circle_24) // Optional placeholder image to show while loading the actual image.
                .into(holder.productImageView);
        holder.itemView.setOnClickListener(v->{
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),ProductPage.class)
                    .putExtra("productId",String.valueOf(homeProductModel.getId())));
        });
    }

    @Override
    public int getItemCount() {
        return homeProductModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImageView;
        public TextView productTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.homepage_product_recycle_view_imageview);
            productTextView = itemView.findViewById(R.id.homepage_product_recycle_view_name_text);
        }
    }
}
