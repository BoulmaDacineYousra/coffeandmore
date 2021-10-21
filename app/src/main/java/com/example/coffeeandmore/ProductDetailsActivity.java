package com.example.coffeeandmore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends AppCompatActivity {
    private TextView name, desc, price, quantity;
    //   ModelProduct Products ;
    private ImageButton add, substract;
    private Button addToCart;
    private DatabaseHelper db;
    private ImageButton favorite;
    int uid;
    Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        name = (TextView) findViewById(R.id.product_name);
        desc = (TextView) findViewById(R.id.product_description);
        price = (TextView) findViewById(R.id.product_price);
        add = (ImageButton) findViewById(R.id.plus);
        substract = (ImageButton) findViewById(R.id.minus);
        quantity = (TextView) findViewById(R.id.quantity);
        addToCart = (Button) findViewById(R.id.add_to_cart);
        favorite = (ImageButton) findViewById(R.id.favorite);


        //retrieve user id
        SessionManagment sessionManagment = new SessionManagment(ProductDetailsActivity.this);
        UserData currentUser = sessionManagment.getSession();
        uid = currentUser.getUid();


        db = new DatabaseHelper(this);

        //retrieve product id
        Intent intent = getIntent();
        ModelProduct products = intent.getParcelableExtra("key");


        name.setText(products.getProductName());
        desc.setText(String.valueOf(products.getProductDescription()));
        price.setText(String.valueOf(products.getProductPrice())+ "DA");

        state = db.FavoriteState(uid, products.getId());
        if (state == true) {
            favorite.setImageResource(R.drawable.heartfull);
        } else {
            favorite.setImageResource(R.drawable.heartempty);

        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qnt = Integer.valueOf(quantity.getText().toString());
                qnt++;
                quantity.setText(String.valueOf(qnt));
            }
        });

        substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qnt = Integer.valueOf(quantity.getText().toString());
                if (qnt != 1) {
                    qnt--;

                }
                quantity.setText(String.valueOf(qnt));
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean added = db.AddToCart(uid, products.getId(), Integer.valueOf(quantity.getText().toString()), Integer.valueOf(quantity.getText().toString()) * products.getProductPrice());
                if (added = true) {
                    Toast.makeText(getApplicationContext(), "le produit a ete ajoutee ", 1).show();
                } else {

                    Toast.makeText(getApplicationContext(), "veuiiler resseyé  ", 1).show();
                }

            }
        });


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (state == false ) {


                    boolean added = db.AddTofavorites(uid, products.getId());
                    if (added = true) {
                        Toast.makeText(getApplicationContext(), "le produit a ete ajoutee ", 1).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "veuiiler resseyé  ", 1).show();
                    }
                    favorite.setImageResource(R.drawable.heartfull);
                    state = true;

                } else {

                    boolean removed = db.RemoveFromfavorites(uid , products.getId());
                    if (removed = true) {
                        Toast.makeText(getApplicationContext(), "le produit a etait retiré  ", 1).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "veuiiler resseyé  ", 1).show();
                    }
                    favorite.setImageResource(R.drawable.heartempty);
                    state = false ;

                }
            }


        });


    }
}