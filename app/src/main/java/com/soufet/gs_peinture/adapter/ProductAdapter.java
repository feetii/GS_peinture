package com.soufet.gs_peinture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.soufet.gs_peinture.R;
import com.soufet.gs_peinture.models.Products;
import com.soufet.gs_peinture.viewholder.ProducViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProducViewHolder>  {
    Context context;
    ArrayList<Products> products;

    public ProductAdapter(Context context, ArrayList<Products> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProducViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_product,parent,false);
        return new ProducViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducViewHolder holder, int position) {
        Picasso.get().load(products.get(position).getImage())
                .into(holder.product_image);
        holder.product_title.setText(products.get(position).getNom());
        holder.product_category.setText(products.get(position).getCategorie());
        holder.product_quantity.setText(String.valueOf(products.get(position).getQuantity()));
        holder.product_desc.setText(products.get(position).getDescription());
        holder.product_code.setText(String.valueOf(products.get(position).getCode_de_produit());
        holder.delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete product
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

