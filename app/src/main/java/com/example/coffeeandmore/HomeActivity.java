package com.example.coffeeandmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rv, categoriesRV;
    private List<ModelProduct> modelList;
    private List<String> categories;
    private CategotyAdapter categotyAdapter;
    private ProductAdapter mainAdapter;
    private DatabaseHelper db;
    private ImageButton cart, favorite;
    private TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cart = (ImageButton) findViewById(R.id.shopping_cart_item);
        favorite = (ImageButton) findViewById(R.id.favorites_items);
        search = (TextView) findViewById(R.id.search_bar);


        modelList = new ArrayList<>();
        categories = new ArrayList<>();
        db = new DatabaseHelper(this);

        categoriesRV = (RecyclerView) findViewById(R.id.categories_rv);


        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        categoriesRV.setLayoutManager(horizontalLayoutManager);

        //  categoriesRV.setLayoutManager(new LinearLayoutManager(this ));


        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));


        modelList = db.GetProducts();
        categories = db.GetCategories();


        categotyAdapter = new CategotyAdapter(this, categories);
        categoriesRV.setAdapter(categotyAdapter);


        mainAdapter = new ProductAdapter(this, modelList);
        rv.setAdapter(mainAdapter);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
                startActivity(intent);
            }
        });


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);

            }
        });

    }
}