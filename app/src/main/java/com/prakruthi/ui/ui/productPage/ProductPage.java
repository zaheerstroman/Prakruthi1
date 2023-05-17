package com.prakruthi.ui.ui.productPage;

import static com.google.firebase.messaging.Constants.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.GetProductDetails;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class ProductPage extends AppCompatActivity implements GetProductDetails.OnProductDataFetched , AddToCart.OnDataFetchedListner{

    String productId;
    TextView ProductName,ProductDescription,CurrentPrice,MRPPrice,ProductDeleveryAddress,Avalable;
    PowerSpinnerView Qty;
    AppCompatButton AddtoCart,BuyNow;
    DotsIndicator dotsIndicator;
    ViewPager2 ProductImagePager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        // Retrieve the product ID from the intent
        productId = getIntent().getStringExtra("productId");
        ProductImagePager = findViewById(R.id.ProductImagePager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        ProductName = findViewById(R.id.ProductName);
        ProductDescription = findViewById(R.id.ProductDescription);
        CurrentPrice = findViewById(R.id.CurrentPrice);
        MRPPrice = findViewById(R.id.MRPPrice);
        ProductDeleveryAddress = findViewById(R.id.ProductDeleveryAddress);
        Avalable = findViewById(R.id.Avalable);
        Qty = findViewById(R.id.Qty);
        AddtoCart = findViewById(R.id.AddtoCart);
        BuyNow = findViewById(R.id.BuyNow);
        GetApiData();

        AddtoCart.setOnClickListener(v -> {
            Loading.show(this);
            AddToCart addToCart = new AddToCart(productId,String.valueOf(Qty.getSelectedIndex()+1),String.valueOf(Qty.getSelectedIndex()+1),"" ,false,this);
            addToCart.fetchData();
        });
    }

    public void GetApiData()
    {
        GetProductDetails getProductDetails = new GetProductDetails(this , productId);
        getProductDetails.fetchData();
    }
    @Override
    public void OnDataFetched(ProductModel productModel) {
        this.runOnUiThread(()->{
            Toast.makeText(this, productModel.getName(), Toast.LENGTH_SHORT).show();
            ProductImagePager.setAdapter(new ProductPagerAdaptor(this, productModel.getAttachments()));
            dotsIndicator.attachTo(ProductImagePager);
            ProductName.setText(productModel.getName());
            ProductDescription.setText(productModel.getDescription());
            CurrentPrice.setText(productModel.getCustomerPrice());
            MRPPrice.setText(productModel.getActualPrice());
            MRPPrice.setPaintFlags(MRPPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ProductDeleveryAddress.setText(Variables.address);
        });

    }

    @Override
    public void OnDataFetchError(String message) {
        this.runOnUiThread(() ->{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void OnCarteditDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void OnErrorFetched(String Error) {
        this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
        });
    }
}