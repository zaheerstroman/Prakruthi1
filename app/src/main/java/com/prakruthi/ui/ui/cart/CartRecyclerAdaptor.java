package com.prakruthi.ui.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.prakruthi.R;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class CartRecyclerAdaptor extends RecyclerView.Adapter<CartRecyclerAdaptor.ViewHolder> {

    ArrayList<CartModal> cartCategoryModalList = new ArrayList<>();
    Context context;

    public CartRecyclerAdaptor(Context context ,  ArrayList<CartModal> cartCategoryModalList) {
        this.cartCategoryModalList = cartCategoryModalList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistlayout, parent, false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            CartModal cartData = cartCategoryModalList.get(position);

            holder.CartProductName.setText(cartData.getName());

            holder.CartProductSubInformation.setText(cartData.getDescription());
            holder.CartProductPrice.setText(cartData.getCustomerPrice());
            holder.CartProductQuantity.setText(String.valueOf(cartData.getQuantity()));

            Glide.with(context)
                    .load(cartCategoryModalList.get(position).getAttachment())
                    .placeholder(R.drawable.baseline_circle_24)
                    .into(holder.CartProductImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cartCategoryModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView CartProductImage;
        public TextView CartProductName, CartProductSubInformation, CartProductPrice, CartProductQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CartProductName = itemView.findViewById(R.id.CartProductName);
            CartProductSubInformation = itemView.findViewById(R.id.CartProductSubInformation);
            CartProductPrice = itemView.findViewById(R.id.CartProductPrice);
            CartProductImage = itemView.findViewById(R.id.CartProductImage);

            CartProductQuantity = itemView.findViewById(R.id.CartProductQuantity);

            CartProductName.setSelected(true);
            CartProductSubInformation.setSelected(true);

        }


    }
}
