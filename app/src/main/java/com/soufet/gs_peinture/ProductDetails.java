package com.soufet.gs_peinture;

import android.os.Bundle;
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
import com.soufet.gs_peinture.models.Products;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    DatabaseReference productRef ,
    cartRef ;
    String productId;
    ImageView product_image,
    back_btn;
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

        product_title_top=findViewById(R.id.product_title_top);
        product_image=findViewById(R.id.product_image);
        product_category=findViewById(R.id.product_category);
        product_description=findViewById(R.id.product_description);
        product_quantity=findViewById(R.id.product_quantity);
        code =findViewById(R.id.product_code);
        seuil =findViewById(R.id.product_seuil);
        code_cat =findViewById(R.id.catigorie_code);


        product_title=findViewById(R.id.product_title);
        back_btn = findViewById(R.id.back);
    }


    private void getProductDetails(){
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.getValue(Products.class);
                product_title.setText(product.getNom());
                product_title_top.setText(product.getNom());
                product_description.setText(product.getDescription());
                product_quantity.setText(String.valueOf(product.getQuantity()));
                product_category.setText(product.getCategorie());
                Picasso.get().load(product.getImage()).into(product_image);
                code.setText(String.valueOf(product.getCode_de_produit()));
                seuil.setText(String.valueOf(product.getSeuil( )) );
               code_cat.setText(String.valueOf(product.getCode_de_categorie( ) ));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}