package com.prakruthi.ui.ui.profile.order_qty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.OrdersQty;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

public class OrderQtyActivity extends AppCompatActivity implements OrdersQty.OnProfileOrderQtyApiHitFetchedListener{

    ShimmerRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_qty);

        SetViews();
        CallApi();

    }

    public void CallApi()
    {
        OrdersQty ordersQty = new OrdersQty(this);
        ordersQty.HitAPi();
        recyclerView.showShimmerAdapter();
    }

    public void SetViews()
    {
        recyclerView = findViewById(R.id.my_orders_qty_recyclerview_List);
    }


    @Override
    public void OnProfileItemOrdersQty(ArrayList<OrdersQtyModal> ordersQtyModal) {
        runOnUiThread( () -> {
            recyclerView.hideShimmerAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new OrdersQtyAdaptor(ordersQtyModal));
        } );
    }

    @Override
    public void OnProfileItemOrdersQtyAPiGivesError(String error) {
        runOnUiThread( () -> {
            recyclerView.hideShimmerAdapter();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.ALERT)
                    .setDismissButtonBackground(R.color.Primary)
                    .setHeading("Uh-Oh")
                    .setDescription("There Are No Orders"+
                            " In Your Account.")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        } );
    }

}