package com.prakruthi.ui.ui.productPage;

import static com.google.firebase.messaging.Constants.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.GetProductDetails;
import com.prakruthi.ui.APIs.SaveWishList;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class ProductPage extends AppCompatActivity implements GetProductDetails.OnProductDataFetched , AddToCart.OnDataFetchedListner, SaveWishList.OnSaveWishListDataFetchedListener {

    String productId;
    TextView ProductName,ProductDescription,CurrentPrice,MRPPrice,ProductDeleveryAddress,Avalable;
    PowerSpinnerView Qty;
    AppCompatButton AddtoCart,BuyNow;
    DotsIndicator dotsIndicator;

    AppCompatButton Wishlist;
    ViewPager2 ProductImagePager;

    boolean in_wishlist;
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
        Wishlist = findViewById(R.id.Product_Save_Wishlist);
        GetApiData();

        Wishlist.setOnClickListener(v->{
            if (in_wishlist)
            {
                SaveWishList saveWishList = new SaveWishList(this,productId);
                saveWishList.HitSaveWishListApi("No");
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.like_outline));
            } else if (!in_wishlist) {
                SaveWishList saveWishList = new SaveWishList(this,productId);
                saveWishList.HitSaveWishListApi("Yes");
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.like_filled));
            }

        });
        AddtoCart.setOnClickListener(v -> {
            Qty.setError(null);
            if (Qty.getText().toString().isEmpty())
            {
                Qty.setError("Select Quantity");
                ObjectAnimator.ofFloat(Qty, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                Qty.requestFocus();
                return;
            }
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
            ProductImagePager.setAdapter(new ProductPagerAdaptor(this, productModel.getAttachments()));
            dotsIndicator.attachTo(ProductImagePager);
            ProductName.setText(productModel.getName());
            ProductDescription.setText(productModel.getDescription());
            CurrentPrice.setText(productModel.getCustomerPrice());
            MRPPrice.setText(productModel.getActualPrice());
            MRPPrice.setPaintFlags(MRPPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ProductDeleveryAddress.setText(Variables.address);
            in_wishlist = productModel.isIn_wishlist();
            if (in_wishlist)
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.like_filled));
        });

    }

    @Override
    public void OnDataFetchError(String message) {
        this.runOnUiThread(() ->{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.FAILED)
                    .setHeading("Uh-Oh")
                    .setDescription("Unexpected error occurred."+
                            " Try again later.")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        });
    }

    @Override
    public void OnCarteditDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
        });

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.SUCCESS)
                    .setHeading("Well Done")
                    .setDescription("Successfully"+
                            " Added Into The Cart")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        });
    }

    @Override
    public void OnErrorFetched(String Error) {
        this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.FAILED)
                    .setHeading("Uh-Oh")
                    .setDescription("Unexpected error occurred."+
                            " Try again later.")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        });
    }

    @Override
    public void OnItemSavedToWishlist(String message) {
        runOnUiThread( () -> {
            GetApiData();
        } );

    }

    @Override
    public void OnSaveWishlistApiGivesError(String error) {
        runOnUiThread( () -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        } );

    }
}