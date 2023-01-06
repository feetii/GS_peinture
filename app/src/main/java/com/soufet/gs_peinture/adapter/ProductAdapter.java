package com.soufet.gs_peinture.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    Dialog dialog = null;
    Products product1;


    public ProductAdapter(Context context, ArrayList<Products> products) {
        this.context = context;
        this.products = products;
        productsRef = FirebaseDatabase
                .getInstance(context.getString(R.string.db_ref))
                .getReference()
                .child("Products");
        productsFull= new ArrayList <>( products );
    }

    @NonNull
    @Override
    public ProducViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_product,parent,false);
        return new ProducViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducViewHolder holder , @SuppressLint("RecyclerView") int position){
        Picasso.get( ).load( products.get( position ).getImage( ) )
                .into( holder.product_image );
        holder.product_title.setText( products.get( position ).getNom( ) );
        holder.product_category.setText( "category: " + products.get( position ).getCategorie( ) );
        holder.product_quantity.setText( "quantité: " + String.valueOf( products.get( position ).getQuantity( ) ) );
        holder.product_desc.setText( products.get( position ).getDescription( ) );
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

        holder.product_quantity.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){

                AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                mbuilder.setMessage( "Voulez-vous vraiment éditer quantity de  " + products.get( position ).getNom( ) + " ?" );
                mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                        mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        final EditText x = new EditText( context );
                        x.setInputType( InputType.TYPE_CLASS_NUMBER );
                        mbuilder.setMessage( "tape votre quantity de  " + products.get( position ).getNom( ) + " ?" );
                        mbuilder.setView( x );
                        mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){
                                if ( checkFields( x ) ) {
                                    product1 = products.get( position );
                                    product1.setQuantity( Integer.parseInt( x.getText( ).toString( ) ) );
                                    productRef = productsRef = FirebaseDatabase
                                            .getInstance( context.getString( R.string.db_ref ) )
                                            .getReference( )
                                            .child( "Products" ).child( product1.getId( ) );
                                    productRef.setValue( product1 );
                                }
                            }
                        } );
                        mbuilder.show( );
                    }
                } ).setNegativeButton( "No" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                    }
                } );
                mbuilder.show( );

            }
        } );
        holder.product_title.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){

                AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                mbuilder.setMessage( "Voulez-vous vraiment éditer ne nom de " + products.get( position ).getNom( ) + " ?" );
                mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                        mbuilder.setTitle( "Éditer  " + products.get( position ).getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        final EditText x = new EditText( context );
                        x.setInputType( InputType.TYPE_CLASS_TEXT );
                        mbuilder.setMessage( "tape votre nom de " + products.get( position ).getNom( ) + " ?" );
                        mbuilder.setView( x );
                        mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){
                                if ( checkFields( x ) ) {
                                    product1 = products.get( position );
                                    product1.setNom( x.getText( ).toString( ) );
                                    productRef = productsRef = FirebaseDatabase
                                            .getInstance( context.getString( R.string.db_ref ) )
                                            .getReference( )
                                            .child( "Products" ).child( product1.getId( ) );
                                    productRef.setValue( product1 );
                                }
                            }
                        } );
                        mbuilder.show( );
                    }
                } ).setNegativeButton( "No" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                    }
                } );
                mbuilder.show( );

            }
        } );
        holder.product_category.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){

                AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                mbuilder.setMessage( "Voulez-vous vraiment éditer le category de " + products.get( position ).getNom( ) + " ?" );
                mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                        mbuilder.setTitle( "Éditer  " + products.get( position ).getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        final EditText x = new EditText( context );
                        x.setInputType( InputType.TYPE_CLASS_TEXT );
                        mbuilder.setMessage( "tape la category de " + products.get( position ).getNom( ) + " ?" );
                        mbuilder.setView( x );
                        mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){
                                if ( checkFields( x ) ) {
                                    product1 = products.get( position );
                                    product1.setCategorie( x.getText( ).toString( ) );
                                    productRef = productsRef = FirebaseDatabase
                                            .getInstance( context.getString( R.string.db_ref ) )
                                            .getReference( )
                                            .child( "Products" ).child( product1.getId( ) );
                                    productRef.setValue( product1 );
                                }
                            }
                        } );
                        mbuilder.show( );
                    }
                } ).setNegativeButton( "No" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                    }
                } );
                mbuilder.show( );

            }
        } );
        holder.product_code.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){

                AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                mbuilder.setMessage( "Voulez-vous vraiment éditer le code de " + products.get( position ).getNom( ) + " ?" );
                mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                        mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        final EditText x = new EditText( context );
                        x.setInputType( InputType.TYPE_CLASS_NUMBER );
                        mbuilder.setMessage( "tape le code de " + products.get( position ).getNom( ) + " ?" );
                        mbuilder.setView( x );
                        mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){
                                if ( checkFields( x ) ) {
                                    product1 = products.get( position );
                                    product1.setCode_de_produit( x.getText( ).toString( ) );
                                    productRef = productsRef = FirebaseDatabase
                                            .getInstance( context.getString( R.string.db_ref ) )
                                            .getReference( )
                                            .child( "Products" ).child( product1.getId( ) );
                                    productRef.setValue( product1 );
                                }
                            }
                        } );
                        mbuilder.show( );
                    }
                } ).setNegativeButton( "No" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                    }
                } );
                mbuilder.show( );

            }
        } );
        holder.product_desc.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){

                AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                mbuilder.setMessage( "Voulez-vous vraiment éditer la description de " + products.get( position ).getNom( ) + " ?" );
                mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( context );
                        mbuilder.setTitle( "Éditer " + products.get( position ).getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        final EditText x = new EditText( context );
                        x.setInputType( InputType.TYPE_CLASS_TEXT );
                        mbuilder.setMessage( "tape la description de " + products.get( position ).getNom( ) + " ?" );
                        mbuilder.setView( x );
                        mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){
                                if ( checkFields( x ) ) {
                                    product1 = products.get( position );
                                    product1.setDescription( x.getText( ).toString( ) );
                                    productRef = productsRef = FirebaseDatabase
                                            .getInstance( context.getString( R.string.db_ref ) )
                                            .getReference( )
                                            .child( "Products" ).child( product1.getId( ) );
                                    productRef.setValue( product1 );
                                }
                            }
                        } );
                        mbuilder.show( );
                    }
                } ).setNegativeButton( "No" , new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){

                    }
                } );
                mbuilder.show( );

            }
        } );
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




    private boolean checkFields(@NonNull EditText x){
        if(x.getText().toString().isEmpty()){
            x.setError("Veuillez définir votre modification");
            return false;

        }else {return true;}
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
                |product.getCode_de_produit().toLowerCase().contains( filterPattern )){
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

