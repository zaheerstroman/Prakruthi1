package com.prakruthi.ui.ui.wishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.GetWishlistDetails;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.search.SearchAdaptor;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WishListRecyclerAdaptor extends RecyclerView.Adapter<WishListRecyclerAdaptor.ViewHolder> {

    List<WishListModal> wishListModals;

    AddToCart.OnDataFetchedListner listner;
    public WishListRecyclerAdaptor(List<WishListModal> wishListModals ,  AddToCart.OnDataFetchedListner listner) {
        this.wishListModals = wishListModals;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlistlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WishListModal wishListModal = wishListModals.get(position);
        holder.wishlist_product_name.setText(wishListModal.getName());
        holder.wishlist_product_price.setText(wishListModal.getCustomerPrice());
        holder.wishlist_product_added_date.setText(wishListModal.getDate());

        holder.wishlist_product_add_to_cart.setOnClickListener(v -> {
            Loading.show(holder.itemView.getContext());
            AddToCart addToCart = new AddToCart("2","1" , "" , "" , false , listner);
            addToCart.fetchData();
        });

    }


    @Override
    public int getItemCount() {
        return wishListModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView wishlist_product_image;
        TextView wishlist_product_name, wishlist_product_price, wishlist_product_status, wishlist_product_added_date;
        AppCompatButton wishlist_product_add_to_cart;
        ImageView wishlist_product_delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlist_product_image = itemView.findViewById(R.id.wishlist_product_image);
            wishlist_product_name = itemView.findViewById(R.id.wishlist_product_name);
            wishlist_product_price = itemView.findViewById(R.id.wishlist_product_price);
            wishlist_product_status = itemView.findViewById(R.id.wishlist_product_status);
            wishlist_product_add_to_cart = itemView.findViewById(R.id.wishlist_product_add_to_cart);
            wishlist_product_added_date = itemView.findViewById(R.id.wishlist_product_added_date);
            wishlist_product_delete = itemView.findViewById(R.id.wishlist_product_delete);


            wishlist_product_name.setSelected(true);
            wishlist_product_name.setSelected(true);


        }
    }
}
