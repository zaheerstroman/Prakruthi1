package com.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.prakruthi.R;

import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerview_List;
    TextView CART_TEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Objects.requireNonNull(getSupportActionBar()).hide();

    }
}