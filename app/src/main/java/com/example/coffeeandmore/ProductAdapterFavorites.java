package com.example.coffeeandmore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapterFavorites extends RecyclerView.Adapter<ProductAdapterFavorites.ViewHolder> {

    public ProductAdapterFavorites(Context context, List<ModelProduct> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    Context context;
    List<ModelProduct> modelList;
    DatabaseHelper db = new DatabaseHelper(context);

    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new ProductAdapterFavorites.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SessionManagment sessionManagment = new SessionManagment(context);
        UserData currentUser = sessionManagment.getSession();
        int  uid = currentUser.getUid();



        String name = modelList.get(position).getProductName();

        int price = modelList.get(position).getProductPrice() ;
        String description = modelList.get(position).getProductDescription();
        int pid = modelList.get(position).getId();



        holder.pName.setText(name);
        holder.pDescription.setText(description);
        holder.pPrice.setText(String.valueOf(price));


        holder.deleteFromFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean cc  =   db.RemoveFromfavorites(uid , pid);
                Toast.makeText(context , " le produit a etait retiré" , 1).show();
                modelList.remove(modelList.get(position));
            }
        });
        holder.seePoduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , ProductDetailsActivity.class) ;
                intent.putExtra("key" , modelList.get(position) );
                context.startActivity(intent) ;

            }
        });




    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pName, pPrice, pDescription;
        ImageButton deleteFromFavorite , seePoduct;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pName = (TextView) itemView.findViewById(R.id.product_name_favorite);
            pPrice = (TextView) itemView.findViewById(R.id.product_price_favorites);
            pDescription = (TextView) itemView.findViewById(R.id.product_description_favorite);

           deleteFromFavorite = (ImageButton) itemView.findViewById(R.id.delete_btn) ;
           seePoduct= (ImageButton) itemView.findViewById(R.id.see_product_favorite);


        }
    }
}
