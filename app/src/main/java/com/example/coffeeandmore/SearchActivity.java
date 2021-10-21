package com.example.coffeeandmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    private RecyclerView rv;
    private List<ModelProduct> modelList;
    private ProductAdapter mainAdapter;
    private DatabaseHelper db;
    private ImageButton search;
    private EditText filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        rv = (RecyclerView) findViewById(R.id.search_rv);
        filter = (EditText) findViewById(R.id.search_bar);
        search = (ImageButton) findViewById(R.id.search_btn)  ;
        db = new DatabaseHelper(this);

        modelList = new ArrayList<>();




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sFilter = filter.getText().toString();
                Toast.makeText(getApplicationContext() , sFilter , 1 ).show();
                modelList = db.search(sFilter);
                if (modelList.isEmpty() ){
                    Toast.makeText(getApplicationContext() , "empty list" , 1).show();h
                }
                mainAdapter = new ProductAdapter(SearchActivity.this , modelList);
                rv.setAdapter(mainAdapter);


            }
        });






    }
}