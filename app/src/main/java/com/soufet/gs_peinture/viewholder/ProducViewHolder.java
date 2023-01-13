package com.soufet.gs_peinture.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.soufet.gs_peinture.R;

public class ProducViewHolder extends RecyclerView.ViewHolder {
    public TextView product_title ,code_catigory,
    product_category,
    product_quantity,
    product_desc,
    product_code;
    public CardView cardView;
    public ImageView product_image,stat,
    delete_product,
    edit_product;
    String id;

    public ProducViewHolder(@NonNull View itemView) {
        super(itemView);

        product_title = itemView.findViewById(R.id.product_title);
        product_category = itemView.findViewById(R.id.product_category);
        product_quantity = itemView.findViewById(R.id.product_quantity);

        product_image = itemView.findViewById(R.id.product_img);
        delete_product = itemView.findViewById(R.id.delete_product);
        product_code = itemView.findViewById(R.id.code_product);
        cardView=itemView.findViewById( R.id.card_view );
        stat=itemView.findViewById( R.id.stat );
    }
}
