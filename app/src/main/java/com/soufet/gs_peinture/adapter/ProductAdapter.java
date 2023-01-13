package com.soufet.gs_peinture.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.soufet.gs_peinture.ProductDetails;
import com.soufet.gs_peinture.R;
import com.soufet.gs_peinture.models.Products;
import com.soufet.gs_peinture.viewholder.ProducViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProducViewHolder>  implements Filterable {
    Context context;
    ArrayList<Products> products;
    ArrayList<Products> productsFull;
    DatabaseReference productsRef,

    productRef;
    StorageReference productImagesRef;

    Dialog dialog = null;
    Products product1;


    public ProductAdapter(Context context, ArrayList<Products> products) {
        this.context = context;
        this.products = products;
        productsRef = FirebaseDatabase
                .getInstance(context.getString(R.string.db_ref))
                .getReference()
                .child("Products");
        productImagesRef=FirebaseStorage.getInstance("gs://gspeinture-e8a43.appspot.com").getReference().child( "Product images") ;
        productsFull= new ArrayList <>( products );
    }

    @NonNull
    @Override
    public ProducViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_product,parent,false);

        return new ProducViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ProducViewHolder holder , @SuppressLint("RecyclerView") int position){
        Picasso.get( ).load( products.get( position ).getImage( ) )
                .into( holder.product_image );
         if (products.get( position ).getQuantity()<=products.get( position ).getSeuil())
         {
             holder.stat.setColorFilter( Color.RED );
         }else holder.stat.setColorFilter( Color.GREEN );
        if (products.get( position ).getQuantity()==0)
        {
            productsRef
                    .child( products.get( position )
                            .getId( ) )
                    .removeValue( ).addOnCompleteListener( new OnCompleteListener <Void>( ) {
                @Override
                public void onComplete(@NonNull Task <Void> task){
                    if ( task.isSuccessful( ) ) {
                        Toast.makeText( context , "Product deleted" , Toast.LENGTH_SHORT ).show( );
                    } else {
                        Toast.makeText( context , task.getException( ).getMessage( ) , Toast.LENGTH_SHORT ).show( );
                    }
                }
            } );
        }
        holder.product_title.setText( products.get( position ).getNom( ) );
        holder.product_category.setText( "category: " + products.get( position ).getCategorie( ) );
        holder.product_quantity.setText( "quantité: " + String.valueOf( products.get( position ).getQuantity( ) ) );

        holder.product_code.setText( "code: " + String.valueOf( products.get( position ).getCode_de_produit( ) ) );
        holder.delete_product.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){
                //delete product
                AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                mbuilder.setTitle( "Delete " + products.get( position ).getNom( ) );

                mbuilder.setIcon( R.drawable.ic_delete_icon );
                mbuilder.setMessage( "Do you really want to delete " + products.get( position ).getNom( ) + " ?" );
                mbuilder.setPositiveButton( "Yes" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        productImagesRef = productImagesRef.child(products.get( position ).getImageId()+".jpeg");
                        productImagesRef.delete();
                        productsRef
                                .child( products.get( position )
                                        .getId( ) )
                                .removeValue( ).addOnCompleteListener( new OnCompleteListener <Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task <Void> task){
                                if ( task.isSuccessful( ) ) {
                                    Toast.makeText( context , "Product deleted" , Toast.LENGTH_SHORT ).show( );
                                } else {
                                    Toast.makeText( context , task.getException( ).getMessage( ) , Toast.LENGTH_SHORT ).show( );
                                }
                            }
                        } );
                    }
                } );
                mbuilder.setNegativeButton( "No" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                    }
                } );
                mbuilder.show( );

            }
        } );

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.coplex);
        holder.itemView.setAnimation( animation );
        holder.itemView.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){
                Intent i = new Intent( context , ProductDetails.class );
                i.putExtra( "productId" , products.get( position ).getId( ) );
                context.startActivity( i );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return products.size();
    }







    @Override
    public Filter getFilter( ){
        return productFilter;
    }
     private  Filter productFilter= new Filter( ) {
         @Override
         protected FilterResults performFiltering(CharSequence charSequence){
            ArrayList<Products> filteredList=new ArrayList <>(  );
            if (charSequence==null||charSequence.length()==0){
                filteredList.addAll(productsFull);
            }else{
            String filterPattern=charSequence.toString().toLowerCase().trim();
            for (Products product :productsFull ){
                if(product.getNom().toLowerCase().contains( filterPattern )|product.getCategorie().toLowerCase().contains( filterPattern )
                |product.getCode_de_produit().toLowerCase().contains( filterPattern )|product.getDescription().toLowerCase().contains( filterPattern )){
                  filteredList.add( product );
                }
            }
            }
           FilterResults results =new FilterResults();
            results.values=filteredList;
            return results;
         }

         @Override
         protected void publishResults(CharSequence charSequence , FilterResults results){
         products.clear();
         products.addAll( (ArrayList)results.values);
         notifyDataSetChanged();
         }
     };
}

