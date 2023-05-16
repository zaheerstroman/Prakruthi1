package com.prakruthi.ui.ui.cart.category;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.Response.CartDataResponse;
import com.prakruthi.databinding.CartlistlayoutBinding;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartRecyclerAdaptor extends RecyclerView.Adapter<CartRecyclerAdaptor.ViewHolder> {
    //    ArrayList<GetCartDetailsResponse.CartData> list;
//    ArrayList<GetCartDetailsResponse.CartData> list;
//    ArrayList<GetCartDetailsResponse.CartData> list = new ArrayList<>();
//    ArrayList<CartModal.CartData> cartCategoryModalList = new ArrayList<>();
    ArrayList<CartDataResponse> cartCategoryModalList = new ArrayList<>();


    //StackOver Flow
//    ArrayList<String> recordings = null; //You are passing this null
//    ArrayList<String> list = null; //You are passing this null


    Context context;

    //    public CartRecyclerAdaptor(Context context, ArrayList<CartDataResponse> cartCategoryModalList) {
    public CartRecyclerAdaptor(ArrayList<CartDataResponse> cartCategoryModalList) {
//        this.context = context;
        this.cartCategoryModalList = cartCategoryModalList;
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
//            CartModal.CartData cartData = cartCategoryModalList.get(position);
            CartDataResponse cartData = cartCategoryModalList.get(position);

            holder.tv_product_name.setText(cartData.getName());

            holder.tv_sub_product_information.setText(cartData.getDescription());
            holder.price_Rate.setText(cartData.getCustomerPrice());
            holder.tv_add_to_cart.setText(cartData.getQuantity());

            Glide.with(holder.itemView.getContext())
                    .load(cartCategoryModalList.get(position).getAttachment())
                    .placeholder(R.drawable.baseline_circle_24)
                    .into(holder.cart_product_image);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onBindViewHolder: " + e);

        }
    }

    @Override
    public int getItemCount() {
//        return 10;
//        return list.size();
        return cartCategoryModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView cart_product_image;
        public TextView tvSubProductInformation, tv_product_name, tv_sub_product_information, price_Rate, tv_add_to_cart;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_sub_product_information = itemView.findViewById(R.id.tv_sub_product_information);
            price_Rate = itemView.findViewById(R.id.price_Rate);
            cart_product_image = itemView.findViewById(R.id.cart_product_image);

            tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);

            tv_product_name.setSelected(true);

        }


    }
}
