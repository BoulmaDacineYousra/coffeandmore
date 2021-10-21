package com.example.coffeeandmore;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategotyAdapter  extends RecyclerView.Adapter<CategotyAdapter.ViewHolder> {
    Context context ;
    List<String> modelList;

    public CategotyAdapter(Context context, List<String> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories , parent, false);
        return new CategotyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        
        holder.CategoryName.setText(modelList.get(position));
        holder.CategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.CategoryName.setTextColor(Color.parseColor("#8C311C"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        
        TextView CategoryName ; 



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            CategoryName = (TextView) itemView.findViewById(R.id.Category) ; 
        }
    }
}
