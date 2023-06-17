package com.prakruthi.ui.ui.profile.order_qty;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersAdaptor;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersModal;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersQtyAdaptor extends RecyclerView.Adapter<OrdersQtyAdaptor.ViewHolder> {

    ArrayList<OrdersQtyModal> ordersQtys;

    public OrdersQtyAdaptor(ArrayList<OrdersQtyModal> ordersQtys){
        this.ordersQtys = ordersQtys;
    }


    @NonNull
    @Override
    public OrdersQtyAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_qty_listlayout, parent, false);
        return new OrdersQtyAdaptor.ViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersQtyAdaptor.ViewHolder holder, int position) {
        OrdersQtyModal ordersQtyModal = ordersQtys.get(position);

        holder.MyOrdersQtyProductName.setText(ordersQtyModal.getName());
        holder.MyOrdersQtyProductSubInformation.setText(ordersQtyModal.getProduct_id());

        Glide.with(holder.itemView.getContext())
                .load(ordersQtyModal.getAttachment())
                .placeholder(R.drawable.baseline_circle_24)
                .into(holder.MyOrdersQtyProductImage);

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setTitle("Enter Text");

            // Set the custom layout for the dialog
            View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_edit_qty, null);
            builder.setView(dialogView);

            // Find the EditText in the dialog layout
            EditText input = dialogView.findViewById(R.id.edittext_input);

            // Set the negative button
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            // Set the positive button initially with null click listener
            builder.setPositiveButton("Submit", null);

            // Create the dialog
            final AlertDialog dialog = builder.create();

            // Override the onClickListener for the positive button
            dialog.setOnShowListener(dialogInterface -> {
                Button submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Perform your condition check here
                        String text = input.getText().toString();
                        if (!text.isEmpty()) {
                            // Condition is met, do something
                            // ...

                            // Dismiss the dialog if needed
                            dialog.dismiss();
                        } else {
                            // Condition is not met, show an error or take appropriate action
                            input.setError("Please enter text");
                        }
                    }
                });
            });

            // Show the dialog
            dialog.show();
        });




    }

    @Override
    public int getItemCount() {
//        return 0;
        return ordersQtys.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView MyOrdersQtyProductImage;
        TextView MyOrdersQtyProductName,MyOrdersQtyProductSubInformation;
        ImageButton MyOrdersQtyProductNextBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MyOrdersQtyProductImage = itemView.findViewById(R.id.MyOrdersQtyProductImage);
            MyOrdersQtyProductName = itemView.findViewById(R.id.MyOrdersQtyProductName);
            MyOrdersQtyProductSubInformation = itemView.findViewById(R.id.MyOrdersQtyProductSubInformation);
            MyOrdersQtyProductNextBtn = itemView.findViewById(R.id.MyOrdersQtyProductNextBtn);
            MyOrdersQtyProductName.setSelected(true);
        }
    }


}

