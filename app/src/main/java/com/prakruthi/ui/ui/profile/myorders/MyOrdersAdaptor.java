package com.prakruthi.ui.ui.profile.myorders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.ui.search.SearchAdaptor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyOrdersAdaptor extends RecyclerView.Adapter<MyOrdersAdaptor.ViewHolder> {

    List<MyOrdersModal> myOrdersModals;


    public MyOrdersAdaptor(List<MyOrdersModal> myOrdersModals){
        this.myOrdersModals = myOrdersModals;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderslistlayout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyOrdersModal myOrdersModal = myOrdersModals.get(position);

        holder.MyOrdersProductName.setText(myOrdersModal.getName());
        holder.MyOrdersProductSubInformation.setText(myOrdersModal.getTracking_status());

        Glide.with(holder.itemView.getContext())
                .load(myOrdersModal.getAttachment())
                .placeholder(R.drawable.baseline_circle_24)
                .into(holder.MyOrdersProductImage);



    }

    @Override
    public int getItemCount() {
        return myOrdersModals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView MyOrdersProductImage;
        TextView MyOrdersProductName,MyOrdersProductSubInformation;
        ImageButton MyOrdersProductNextBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MyOrdersProductImage = itemView.findViewById(R.id.MyOrdersProductImage);
            MyOrdersProductName = itemView.findViewById(R.id.MyOrdersProductName);
            MyOrdersProductSubInformation = itemView.findViewById(R.id.MyOrdersProductSubInformation);
            MyOrdersProductNextBtn = itemView.findViewById(R.id.MyOrdersProductNextBtn);
            MyOrdersProductName.setSelected(true);
        }
    }
}
