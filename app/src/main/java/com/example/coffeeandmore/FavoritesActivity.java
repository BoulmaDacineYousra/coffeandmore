package com.example.coffeeandmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView rvh;
    private List<ModelProduct> modelList;
    private ProductAdapterFavorites mainAdapter ;
    private  DatabaseHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);


        SessionManagment sessionManagment = new SessionManagment(FavoritesActivity.this );
        UserData currentUser = sessionManagment.getSession();
        int  uid = currentUser.getUid();

        modelList = new ArrayList<>();
        db = new DatabaseHelper(this);


        rvh = (RecyclerView) findViewById(R.id.fav) ;
        rvh.setLayoutManager(new LinearLayoutManager(this ));


        modelList = db.GetFavorites(uid);


        mainAdapter = new ProductAdapterFavorites(this , modelList) ;
        rvh.setAdapter(mainAdapter);


    }
}