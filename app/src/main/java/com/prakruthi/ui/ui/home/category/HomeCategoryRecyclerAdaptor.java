package com.prakruthi.ui.ui.home.category;

import static com.google.firebase.messaging.Constants.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetProductsList;
import com.prakruthi.ui.ui.home.HomeFragment;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCategoryRecyclerAdaptor extends RecyclerView.Adapter<HomeCategoryRecyclerAdaptor.ViewHolder> {

    private List<HomeCategoryModal> homeCategoryModalList;
    GetProductsList.OnCategoryProductsFetchedListner listner;

    public HomeCategoryRecyclerAdaptor(List<HomeCategoryModal> homeCategoryModalList , GetProductsList.OnCategoryProductsFetchedListner listner) {
        this.homeCategoryModalList = homeCategoryModalList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_category_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeCategoryModal homeCategoryModal = homeCategoryModalList.get(position);
        holder.productTextView.setText(homeCategoryModal.getName());
        Glide.with(holder.itemView.getContext())
                .load(homeCategoryModal.getAttachment())
                .placeholder(R.drawable.baseline_circle_24) // Optional placeholder image to show while loading the actual image.
                .into(holder.productImageView);

        if (position % 2 == 0) {
            holder.productImageView.setElevation(8f); // Set elevation for even positions.
        } else {
            holder.productImageView.setElevation(0f); // Remove elevation for odd positions.
        }
        holder.itemView.setOnClickListener(v->{
            HomeFragment.HomeShimmerProductRecyclerView.showShimmerAdapter();
            String categoryid = String.valueOf(homeCategoryModalList.get(position).getId());
            GetProductsList getProductsList = new GetProductsList(listner,categoryid);
            getProductsList.HitGetProductListAPi();
        });
    }


    @Override
    public int getItemCount() {
        return homeCategoryModalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView productImageView;
        public TextView productTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.Products_Image_View);
            productTextView = itemView.findViewById(R.id.Products_Text_View);
            productTextView.setSelected(true);
        }
    }
}