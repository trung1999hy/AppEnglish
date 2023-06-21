package com.example.purchase;

import static com.android.billingclient.api.BillingClient.ProductType.INAPP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.example.englishttcm.R;
import com.example.englishttcm.application.MyApplication;
import com.example.englishttcm.common.Constant;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class InAppPurchaseActivity extends AppCompatActivity implements InAppPurchaseAdapter.OnClickListener {
    private InAppPurchaseAdapter adapter;
    private BillingClient billingClient;
    private Handler handler;
    private List<ProductDetails> productDetailsList;
    private OnPurchaseResponse onPurchaseResponse;

    private ImageView imvBack;
    private RecyclerView listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_in_app);
        imvBack = findViewById(R.id.imvBack);
        listData = findViewById(R.id.rcvInApp);
        initViews();
        imvBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    private void initViews() {
        adapter = new InAppPurchaseAdapter();
        listData.setHasFixedSize(true);
        listData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listData.setAdapter(adapter);
        adapter.setOnClickListener(this);
        productDetailsList = new ArrayList<>();
        handler = new Handler();
        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(
                        (billingResult, list) -> {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                                for (Purchase purchase : list) {
                                    verifyInAppPurchase(purchase);
                                }
                            }
                        }
                ).build();
        establishConnection();
    }

    void establishConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    showProducts();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                establishConnection();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void showProducts() {
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(getInAppProductList())
                .build();
        billingClient.queryProductDetailsAsync(
                params,
                (billingResult, prodDetailsList) -> {
                    // Process the result
                    productDetailsList.clear();
                    handler.postDelayed(() -> {
                        productDetailsList.addAll(prodDetailsList);
                        adapter.setData(this, productDetailsList);
                        if (prodDetailsList.size() == 0)
                            Toast.makeText(InAppPurchaseActivity.this, "prodDetailsList, size = 0", Toast.LENGTH_SHORT).show();
                    }, 2000);
                }
        );
    }

    private ImmutableList<QueryProductDetailsParams.Product> getInAppProductList() {
        ImmutableList<QueryProductDetailsParams.Product> productList = ImmutableList.of(
                //Product 1
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_1_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 2
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_2_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 3
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_3_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 4
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_4_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 5
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_5_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 6
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_6_COIN)
                        .setProductType(INAPP)
                        .build(),
                //Product 7
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_7_COIN)
                        .setProductType(INAPP)
                        .build(),
                //Product 8
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_8_COIN)
                        .setProductType(INAPP)
                        .build(),
                //Product 9
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constant.KEY_9_COIN)
                        .setProductType(INAPP)
                        .build()
        );
        return productList;
    }

    void verifyInAppPurchase(Purchase purchases) {
        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchases.getPurchaseToken())
                .build();
// lắng nghe trả về trạng thái mua
        billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                String proId = purchases.getProducts().get(0);
                int quantity = purchases.getQuantity();
                setPurchaseResponse(this::setupResult);
                onPurchaseResponse.onResponse(proId, quantity);
                allowMultiplePurchases(purchases);
                Toast.makeText(InAppPurchaseActivity.this, "verifyInAppPurchase Mua ok--> " + proId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void allowMultiplePurchases(Purchase purchase) {
        // gọi thằng này để mua nhiều lần
        ConsumeParams consumeParams = ConsumeParams
                .newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
        billingClient.consumeAsync(consumeParams, new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String s) {
                Toast.makeText(InAppPurchaseActivity.this, " Resume item ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickItem(ProductDetails item) {
        launchPurchaseFlow(item);
    }

    private void launchPurchaseFlow(ProductDetails productDetails) {
        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .build()
                );
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();
        billingClient.launchBillingFlow(this, billingFlowParams);
    }

    protected void onResume() {
        super.onResume();
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(INAPP).build(),
                (billingResult, list) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        for (Purchase purchase : list) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                verifyInAppPurchase(purchase);
                            }
                        }
                    }
                }
        );
    }

    private void setupResult(String proId, int quantity) {
        Intent intent = new Intent();
        int totalCoin = MyApplication.getInstance().getPreference().getValueCoin();
        int remainCoin = totalCoin + getCoinFromKey(proId) * quantity;
        MyApplication.getInstance().getPreference().setValueCoin(remainCoin);
        intent.putExtra(Constant.COIN_ORDER_RESULT, remainCoin + "");
        setResult(Activity.RESULT_OK, intent);
        runOnUiThread(this::onBackPressed);
    }

    private int getCoinFromKey(String coin) {
        switch (coin) {
            case Constant.KEY_1_COIN:
                return 50;
            case Constant.KEY_2_COIN:
                return 100;
            case Constant.KEY_3_COIN:
                return 200;
            case Constant.KEY_4_COIN:
                return 400;
            case Constant.KEY_5_COIN:
                return 600;
            case Constant.KEY_6_COIN:
                return 700;
            case Constant.KEY_7_COIN:
                return 25;
            case Constant.KEY_8_COIN:
                return 300;
            case Constant.KEY_9_COIN:
                return 650;
            default:
                return 0;
        }
    }

    interface OnPurchaseResponse {
        void onResponse(String proId, int quantity);
    }

    private void setPurchaseResponse(OnPurchaseResponse onPurchaseResponse) {
        this.onPurchaseResponse = onPurchaseResponse;
    }
}
