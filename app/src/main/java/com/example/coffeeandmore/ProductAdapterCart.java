package com.example.coffeeandmore;

import android.content.Context;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapterCart extends RecyclerView.Adapter<ProductAdapterCart.ViewHolder> {
    Context context;
    List<ModelProduct> modelList;
    DatabaseHelper db = new DatabaseHelper(context) ;
    int quantity , uid ;


    public ProductAdapterCart(Context context, List<ModelProduct> modelList) {
        this.context = context;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoping_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        SessionManagment sessionManagment = new SessionManagment(context);
        UserData currentUser = sessionManagment.getSession();
        uid = currentUser.getUid();

        String name = modelList.get(position).getProductName();
        int price = modelList.get(position).getProductPrice() ;
        String description = modelList.get(position).getProductDescription();
        int pid = modelList.get(position).getId();
        quantity = 5 ;   //db.getQuantity( uid , pid) ;



        holder.pname.setText(name);
        holder.pdescription.setText(description);
        holder.pPrice.setText(String.valueOf(price));
        holder.quantityy.setText(String.valueOf(quantity));


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity != 1) {
                    quantity--;
                }
               holder.quantityy.setText(String.valueOf(quantity));
              //  db.UpdateQuatity(uid , pid , quantity);
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity != 1) {
                    quantity++;
                }
                holder.quantityy.setText(String.valueOf(quantity));
              //  db.UpdateQuatity(uid , pid , quantity);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.RemoveFromCart(uid, pid );
                Toast.makeText(context , " le produit a etait retiré" , 1).show();
                modelList.remove(modelList.get(position));
            }
        });



    }

    //}



    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView productIm;
        TextView pname, pdescription, pPrice, quantityy, totalePrice;
        ImageButton plus, minus , delete ;

        public ViewHolder(View itemView) {
            super(itemView);

         //   productIm = (ImageView) itemView.findViewById(R.id.procuct_image_cart);


            pname = (TextView) itemView.findViewById(R.id.product_name_cart);
            pdescription = (TextView) itemView.findViewById(R.id.procuct_decription_cart);
            pPrice = (TextView) itemView.findViewById(R.id.prix_unitaire);
            quantityy = (TextView) itemView.findViewById(R.id.quantity_cart);
          //  totalePrice = (TextView) itemView.findViewById(R.id.total_price);

            minus = (ImageButton) itemView.findViewById(R.id.minus_btn);
            plus = (ImageButton) itemView.findViewById(R.id.plus_cart);
            delete= (ImageButton) itemView.findViewById(R.id.corbiel);


        }
    }
}
