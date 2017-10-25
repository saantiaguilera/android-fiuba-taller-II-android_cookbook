package com.saantiaguilera.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.saantiaguilera.models.Buyer;
import com.saantiaguilera.models.Product;
import com.saantiaguilera.models.R;
import com.saantiaguilera.models.Seller;
import com.saantiaguilera.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by saguilera on 10/25/17.
 */

public class MainActivity extends AppCompatActivity {

    @NonNull
    private TextView responseTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final TextView serializeBuyerTextView = findViewById(R.id.activity_main_serializeBuyer);
        final TextView serializeSellerTextView = findViewById(R.id.activity_main_serializeSeller);
        final TextView parseBuyerTextView = findViewById(R.id.activity_main_parseBuyer);
        final TextView parseSellerTextView = findViewById(R.id.activity_main_parseSeller);

        responseTextView = findViewById(R.id.activity_main_jsontext);

        serializeBuyerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serialize(mockBuyer());
            }
        });

        serializeSellerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serialize(mockSeller());
            }
        });

        parseBuyerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parse(new Gson().toJson(mockBuyer()), Buyer.class);
            }
        });

        parseSellerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parse(new Gson().toJson(mockSeller()), Seller.class);
            }
        });
    }

    void serialize(@NonNull User user) {
        responseTextView.setText(new Gson().toJson(user));
    }

    void parse(@NonNull String json, @NonNull Class<? extends User> outputClazz) {
        responseTextView.setText(new Gson().fromJson(json, outputClazz).toString());
    }

    @NonNull
    Buyer mockBuyer() {
        Buyer buyer = new Buyer();
        buyer.setId(new Random().nextInt());
        buyer.setName("test-" + String.valueOf(buyer.getId()));

        List<Product> products = new ArrayList<>();
        products.add(mockProduct());
        products.add(mockProduct());
        products.add(mockProduct());

        buyer.setCatalog(products);

        return buyer;
    }

    @NonNull
    Seller mockSeller() {
        Seller seller = new Seller();
        seller.setId(new Random().nextInt());
        seller.setName("test-" + String.valueOf(seller.getId()));

        List<Product> products = new ArrayList<>();
        products.add(mockProduct());
        products.add(mockProduct());
        products.add(mockProduct());

        seller.setFavorites(products);

        return seller;
    }

    @NonNull
    Product mockProduct() {
        Product product = new Product();
        product.setId(new Random().nextInt());
        product.setInnerSchemaId(new Random().nextInt());
        product.setName("test-" + String.valueOf(product.getId() + product.getInnerSchemaId()));
        product.setPrice(new Random().nextInt());
        product.setTags(null);
        return product;
    }

}
