package com.example.coffeeandmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {
    private RecyclerView shopping ;
    private List<ModelProduct> modelList;
    private ProductAdapterCart mainAdapter ;
    private  DatabaseHelper db ;
    private ImageButton cart , favorite , search ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);




        SessionManagment sessionManagment = new SessionManagment(ShoppingCartActivity.this );
        UserData currentUser = sessionManagment.getSession();
        int  uid = currentUser.getUid();

        modelList = new ArrayList<>();
        db = new DatabaseHelper(this);


        shopping = (RecyclerView) findViewById(R.id.shopping) ;
        shopping.setLayoutManager(new LinearLayoutManager(this ));


        modelList = db.GetCartShopping(uid);


        mainAdapter = new ProductAdapterCart(this , modelList) ;
        shopping.setAdapter(mainAdapter);


    }
}