package com.prakruthi.ui.ui.productPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetProductDetails;

public class ProductPage extends AppCompatActivity implements GetProductDetails.OnProductDataFetched {

    String productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        // Retrieve the product ID from the intent
        productId = getIntent().getStringExtra("productId");
        GetApiData();
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
        });

    }

    @Override
    public void OnDataFetchError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}