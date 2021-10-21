package com.example.coffeeandmore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>  {
    Context context ;
    List<ModelProduct> modelList;
    Boolean state ;
    DatabaseHelper db = new DatabaseHelper(context) ;




    public ProductAdapter(Context context, List<ModelProduct> modelList)  {
        this.context = context;
        this.modelList = modelList;
      //  this.state = db.FavoriteState(modelList.get(possition))

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {        String name = modelList.get(position).getProductName();
        int price = modelList.get(position).getProductPrice() ;
        String description = modelList.get(position).getProductDescription();
        int pid = modelList.get(position).getId();


        holder.productName.setText(name);
        holder.productPrice.setText(String.valueOf(price));
        holder.productDescription.setText(description);

        holder.seeProduct.setOnClickListener(new View.OnClickListener() {
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

        ImageView productImage ;
        TextView productName , productDescription , productPrice ;

        ImageButton  seeProduct;


        public ViewHolder( View itemView) {
            super(itemView);


           // productImage = (ImageView) itemView.findViewById(R.id.Product_image);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productDescription = (TextView) itemView.findViewById(R.id.Product_description);
            productPrice = (TextView) itemView.findViewById(R.id.poduct_price);
            seeProduct =(ImageButton) itemView.findViewById(R.id.see_product_favorite);



        }
    }
}
