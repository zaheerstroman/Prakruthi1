package com.prakruthi.ui.ui.productPage;

import static com.google.firebase.messaging.Constants.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddRecentViewProductsAPI;
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
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

public class ProductPage extends AppCompatActivity implements GetProductDetails.OnProductDataFetched , AddToCart.OnDataFetchedListner, SaveWishList.OnSaveWishListDataFetchedListener {

    String productId;
    TextView ProductName,ProductDescription,CurrentPrice,MRPPrice,ProductDeleveryAddress,Avalable,Ratingcount;
    PowerSpinnerView Qty;
    AppCompatButton AddtoCart,BuyNow;
    DotsIndicator dotsIndicator;

    AppCompatButton Wishlist;
    ViewPager2 ProductImagePager;

    ScaleRatingBar ratingBar;
    ShimmerRecyclerView ReviewsrecyclerView;


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
        ratingBar = findViewById(R.id.simpleRatingBar);
        Ratingcount = findViewById(R.id.RatingCount);
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
            AddToCart addToCart = new AddToCart(productId,String.valueOf(Qty.getSelectedIndex()+1),String.valueOf(Qty.getSelectedIndex()+1) ,false,this);
            addToCart.fetchData();
        });
    }

    public void GetApiData()
    {
        GetProductDetails getProductDetails = new GetProductDetails(this , productId);
        getProductDetails.fetchData();
        AddRecentViewProductsAPI addRecentViewProductsAPI = new AddRecentViewProductsAPI(productId);
        addRecentViewProductsAPI.HitRecentApi();
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
            ratingBar.setRating(Float.parseFloat(productModel.getRating()));
            Ratingcount.setText(productModel.getCount_rating());
            ratingBar.setScrollable(false);
            ratingBar.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
                {
                    // Inflate the custom layout
                    View bottomSheetView = getLayoutInflater().inflate(R.layout.product_reviews_bottom_popup, null);

                    // Find the RecyclerView in the layout
                    ReviewsrecyclerView = bottomSheetView.findViewById(R.id.recyclerView);

                    // Set up your RecyclerView (e.g., set adapter, layout manager, etc.)
                    ReviewsrecyclerView.showShimmerAdapter();
                    Log.e(TAG, "OnDataFetched: " );
                    // Create the bottom sheet dialog
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductPage.this);
                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
                    Handler handler = new Handler(Looper.getMainLooper());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed( () -> {

                            ReviewsrecyclerView.setLayoutManager(new LinearLayoutManager(ProductPage.this));
                            ReviewsrecyclerView.setAdapter(new RecyclerView.Adapter() {
                                @NonNull
                                @Override
                                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_reviews_bottom_popup_recycler, parent, false);
                                    return new ReviewViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                                }

                                @Override
                                public int getItemCount() {
                                    return 20;
                                }

                                // ViewHolder class for the item view
                                class ReviewViewHolder extends RecyclerView.ViewHolder {

                                    ReviewViewHolder(View itemView) {
                                        super(itemView);
                                    }
                                }
                            });
                            ReviewsrecyclerView.hideShimmerAdapter();
                        }, 2000);
                    }
                }

            });

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