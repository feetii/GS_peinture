package com.soufet.gs_peinture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.soufet.gs_peinture.models.Products;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    DatabaseReference productRef ,
            productsRef ;
    String productId;
    StorageReference productImagesRef;
    ImageView product_image,add,soustra,
    back_btn;
    Products product1;
    TextView product_title_top,
    product_title,
    product_category,code,code_cat,seuil, product_description, product_quantity;

    FirebaseAuth mauth;
    Products product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().hide();//Ocultar ActivityBar anterior
        // get selected product id
        productId = getIntent().getStringExtra("productId");

        initializationOfFields();
        getProductDetails();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void initializationOfFields(){
        mauth = FirebaseAuth.getInstance();
        productRef= FirebaseDatabase.getInstance(getString(R.string.db_ref)).getReference()
                    .child("Products").child(productId);
        productImagesRef= FirebaseStorage.getInstance("gs://gspeinture-e8a43.appspot.com").getReference().child( "Product images") ;
        product_title_top=findViewById(R.id.product_title_top);
        product_image=findViewById(R.id.product_image);
        product_category=findViewById(R.id.product_category);
        product_description=findViewById(R.id.product_description);
        product_quantity=findViewById(R.id.product_quantity);
        code =findViewById(R.id.product_code);
        seuil =findViewById(R.id.product_seuil);
        code_cat =findViewById(R.id.catigorie_code);
        add=findViewById( R.id.add );
        soustra=findViewById( R.id.soustration );
        product_title=findViewById(R.id.product_title);
        back_btn = findViewById(R.id.back);
    }


    private void getProductDetails(){
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.getValue(Products.class);
                product_title.setText(product.getNom());
                product_title.setOnLongClickListener( new View.OnLongClickListener( ) {
                    @Override
                    public boolean onLongClick(View view){
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                        mbuilder.setTitle( "Éditer " + product.getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        mbuilder.setMessage( "Voulez-vous vraiment éditer ne nom de " + product.getNom( ) + " ?" );
                        mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){

                                AlertDialog.Builder mbuilder = new AlertDialog.Builder(ProductDetails.this );
                                mbuilder.setTitle( "Éditer  " + product.getNom( ) );
                                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                                final EditText x = new EditText( ProductDetails.this);
                                x.setInputType( InputType.TYPE_CLASS_TEXT );
                                mbuilder.setMessage( "tape votre nom de " + product.getNom( ) + " ?" );
                                mbuilder.setView( x );
                                mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface , int i){
                                        if ( checkFields( x ) ) {
                                            product1 = product;
                                            product1.setNom( x.getText( ).toString( ) );
                                            productRef = productsRef = FirebaseDatabase
                                                    .getInstance(getString( R.string.db_ref ) )
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

                        return true;
                    }
                } );
                add.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view){
                        product1 = product;
                        product1.setQuantity( product1.getQuantity()+1 );
                        productRef = productsRef = FirebaseDatabase
                                .getInstance( getString( R.string.db_ref ) )
                                .getReference( )
                                .child( "Products" ).child( product1.getId( ) );
                        productRef.setValue( product1 );
                    }
                } );

                product_title_top.setText(product.getNom());
                product_description.setText(product.getDescription());
                product_description.setOnLongClickListener( new View.OnLongClickListener( ) {
                    @Override
                    public boolean onLongClick(View view){
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                        mbuilder.setTitle( "Éditer " + product.getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        mbuilder.setMessage( "Voulez-vous vraiment éditer la description de " + product.getNom( ) + " ?" );
                        mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){

                                AlertDialog.Builder mbuilder = new AlertDialog.Builder(ProductDetails.this );
                                mbuilder.setTitle( "Éditer " + product.getNom( ) );
                                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                                final EditText x = new EditText( ProductDetails.this);
                                x.setInputType( InputType.TYPE_CLASS_TEXT );
                                mbuilder.setMessage( "tape la description de " + product.getNom( ) + " ?" );
                                mbuilder.setView( x );
                                mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface , int i){
                                        if ( checkFields( x ) ) {
                                            product1 = product;
                                            product1.setDescription( x.getText( ).toString( ) );
                                            productRef = productsRef = FirebaseDatabase
                                                    .getInstance(getString( R.string.db_ref ) )
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

                        return true;
                    }
                } );
                product_quantity.setText(String.valueOf(product.getQuantity()));
                product_quantity.setOnLongClickListener( new View.OnLongClickListener( ) {
                    @Override
                    public boolean onLongClick(View view){
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                        mbuilder.setTitle( "Éditer " + product.getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        mbuilder.setMessage( "Voulez-vous vraiment éditer quantity de  " + product.getNom( ) + " ?" );
                        mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){

                                AlertDialog.Builder mbuilder = new AlertDialog.Builder(ProductDetails.this );
                                mbuilder.setTitle( "Éditer " + product.getNom( ) );
                                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                                final EditText x = new EditText( ProductDetails.this );
                                x.setInputType( InputType.TYPE_CLASS_NUMBER );
                                mbuilder.setMessage( "tape votre quantity de  " + product.getNom( ) + " ?" );
                                mbuilder.setView( x );
                                mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface , int i){
                                        if ( checkFields( x ) ) {
                                            if (Integer.parseInt( x.getText( ).toString( ) )==0 ) {

                                                Intent j = new Intent(ProductDetails.this, Listeofproducts.class);
                                                startActivity(j);
                                                productImagesRef = productImagesRef.child(product.getImageId()+".jpeg");
                                                productImagesRef.delete();
                                                productsRef
                                                        .child( product
                                                                .getId( ) )
                                                        .removeValue( ).addOnCompleteListener( new OnCompleteListener <Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task <Void> task){
                                                        if ( task.isSuccessful( ) ) {
                                                            Toast.makeText(ProductDetails.this , "Product deleted" , Toast.LENGTH_SHORT ).show( );
                                                        } else {
                                                            Toast.makeText( ProductDetails.this , task.getException( ).getMessage( ) , Toast.LENGTH_SHORT ).show( );
                                                        }
                                                    }
                                                } );
                                            }else
                                            product1 = product;
                                            product1.setQuantity( Integer.parseInt( x.getText( ).toString( ) ) );
                                            productRef = productsRef = FirebaseDatabase
                                                    .getInstance( getString( R.string.db_ref ) )
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
                        return true;
                    }
                } );
                soustra.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view){
                        if (product.getQuantity()==1)
                        {   AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                            mbuilder.setTitle( "supprimer " + product.getNom( ) );
                            mbuilder.setIcon( R.drawable.ic_delete_icon );
                            mbuilder.setMessage( "Voulez-vous vraiment suprimer  " + product.getNom( ) + " ?" );
                            mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                                @Override
                                public void onClick(DialogInterface dialogInterface , int i){
                                    productImagesRef = productImagesRef.child(product.getImageId()+".jpeg");
                                    productImagesRef.delete();
                                    productsRef
                                            .child( product
                                                    .getId( ) )
                                            .removeValue( ).addOnCompleteListener( new OnCompleteListener <Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task <Void> task){
                                            if ( task.isSuccessful( ) ) {
                                                Toast.makeText( ProductDetails.this , "Product deleted" , Toast.LENGTH_SHORT ).show( );
                                            } else {
                                                Toast.makeText( ProductDetails.this , task.getException( ).getMessage( ) , Toast.LENGTH_SHORT ).show( );
                                            }
                                        }
                                    } );
                                    Intent j = new Intent( ProductDetails.this , Listeofproducts.class );

                                    startActivity( j );

                                    mbuilder.show( );
                                }

                            } ).setNegativeButton( "No" , new DialogInterface.OnClickListener( ) {
                                @Override
                                public void onClick(DialogInterface dialogInterface , int i){
                                    product1 = product;
                                    product1.setQuantity( product1.getQuantity()+1 );
                                    productRef = productsRef = FirebaseDatabase
                                            .getInstance( getString( R.string.db_ref ) )
                                            .getReference( )
                                            .child( "Products" ).child( product1.getId( ) );
                                    productRef.setValue( product1 );
                                }
                            } );
                            mbuilder.show( ); }
                        else
                        product1 = product;
                        product1.setQuantity( product1.getQuantity()-1 );
                        productRef = productsRef = FirebaseDatabase
                                .getInstance( getString( R.string.db_ref ) )
                                .getReference( )
                                .child( "Products" ).child( product1.getId( ) );
                        productRef.setValue( product1 );
                    }
                } );

                product_category.setText(product.getCategorie());
                product_category.setOnLongClickListener( new View.OnLongClickListener( ) {
                    @Override
                    public boolean onLongClick(View view){
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                        mbuilder.setTitle( "Éditer " + product.getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        mbuilder.setMessage( "Voulez-vous vraiment éditer la category de  " + product.getNom( ) + " ?" );
                        mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){

                                AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                                mbuilder.setTitle( "Éditer  " + product.getNom( ) );
                                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                                final EditText x = new EditText( ProductDetails.this );
                                x.setInputType( InputType.TYPE_CLASS_TEXT );
                                mbuilder.setMessage( "tape la category de " + product.getNom( ) + " ?" );
                                mbuilder.setView( x );
                                mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface , int i){
                                        if ( checkFields( x ) ) {
                                            product1 = product;
                                            product1.setCategorie( x.getText( ).toString( ) );
                                            productRef = productsRef = FirebaseDatabase
                                                    .getInstance(getString( R.string.db_ref ) )
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
                        return true;
                    }
                } );
                Picasso.get().load(product.getImage()).into(product_image);
                code.setText(String.valueOf(product.getCode_de_produit()));
                code.setOnLongClickListener( new View.OnLongClickListener( ) {
                    @Override
                    public boolean onLongClick(View view){
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                        mbuilder.setTitle( "Éditer " + product.getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        mbuilder.setMessage( "Voulez-vous vraiment éditer le code de " + product.getNom( ) + " ?" );
                        mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){

                                AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                                mbuilder.setTitle( "Éditer " + product.getNom( ) );
                                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                                final EditText x = new EditText( ProductDetails.this);
                                x.setInputType( InputType.TYPE_CLASS_NUMBER );
                                mbuilder.setMessage( "tape le code de " + product.getNom( ) + " ?" );
                                mbuilder.setView( x );
                                mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface , int i){
                                        if ( checkFields( x ) ) {
                                            product1 = product;
                                            product1.setCode_de_produit( x.getText( ).toString( ) );
                                            productRef = productsRef = FirebaseDatabase
                                                    .getInstance(getString( R.string.db_ref ) )
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

                        return true;
                    }
                } );
                seuil.setText(String.valueOf(product.getSeuil( )) );
                seuil.setOnLongClickListener( new View.OnLongClickListener( ) {
                    @Override
                    public boolean onLongClick(View view){
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                        mbuilder.setTitle( "Éditer " + product.getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        mbuilder.setMessage( "Voulez-vous vraiment éditer le seuil de " + product.getNom( ) + " ?" );
                        mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){

                                AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                                mbuilder.setTitle( "Éditer " + product.getSeuil() );
                                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                                final EditText x = new EditText( ProductDetails.this);
                                x.setInputType( InputType.TYPE_CLASS_NUMBER );
                                mbuilder.setMessage( "tape le seuil de " + product.getNom( ) + " ?" );
                                mbuilder.setView( x );
                                mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface , int i){
                                        if ( checkFields( x ) ) {
                                            product1 = product;
                                            product1.setSeuil(Integer.parseInt( x.getText( ).toString( ) ));
                                            productRef = productsRef = FirebaseDatabase
                                                    .getInstance(getString( R.string.db_ref ) )
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

                        return true;
                    }
                } );
                code_cat.setText(String.valueOf(product.getCode_de_categorie( ) ));
                code_cat.setOnLongClickListener( new View.OnLongClickListener( ) {
                    @Override
                    public boolean onLongClick(View view){
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                        mbuilder.setTitle( "Éditer " + product.getNom( ) );
                        mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                        mbuilder.setMessage( "Voulez-vous vraiment éditer le code de catigorie" + product.getNom( ) + " ?" );
                        mbuilder.setPositiveButton( "Oui" , new DialogInterface.OnClickListener( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i){

                                AlertDialog.Builder mbuilder = new AlertDialog.Builder( ProductDetails.this );
                                mbuilder.setTitle( "Éditer " + product.getCode_de_categorie() );
                                mbuilder.setIcon( R.drawable.ic__52547_edit_mode_icon );
                                final EditText x = new EditText( ProductDetails.this);
                                x.setInputType( InputType.TYPE_CLASS_NUMBER );
                                mbuilder.setMessage( "tape le code de catigory de " + product.getNom( ) + " ?" );
                                mbuilder.setView( x );
                                mbuilder.setPositiveButton( "ok" , new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface , int i){
                                        if ( checkFields( x ) ) {
                                            product1 = product;
                                            product1.setCode_de_categorie( x.getText( ).toString( ) );
                                            productRef = productsRef = FirebaseDatabase
                                                    .getInstance(getString( R.string.db_ref ) )
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

                        return true;
                    }
                } );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private boolean checkFields(@NonNull EditText x){
        if(x.getText().toString().isEmpty()){
            x.setError("Veuillez définir votre modification");
            return false;

        }else {return true;}
    }


}