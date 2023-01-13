package com.soufet.gs_peinture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soufet.gs_peinture.adapter.ProductAdapter;
import com.soufet.gs_peinture.models.Products;

import java.util.ArrayList;

public class Listeofproducts extends AppCompatActivity {
    RecyclerView product_list;
    FloatingActionButton add;
    DatabaseReference productRef;
    ProductAdapter adapter;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeofproducts);
        getSupportActionBar();
        product_list=findViewById(R.id.product_list);
         cardView = findViewById(R.id.card_view);
        add=findViewById(R.id.addBtn);

        productRef= FirebaseDatabase.getInstance(getString(R.string.db_ref)).getReference().child("Products");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Listeofproducts.this,NewProduct.class);
                startActivity(i);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(Listeofproducts.this);

        product_list.setLayoutManager(manager);



        fetchDataFromDB();


    }



    private void fetchDataFromDB(){
        ArrayList<Products> products = new ArrayList<>();
        // getting data from the database
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot oneSnapshot : snapshot.getChildren()){
                    Products product = oneSnapshot.getValue(Products.class);
                    products.add(product);
                }
                 adapter = new ProductAdapter(Listeofproducts.this,products);
                product_list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

       MenuInflater inflater=getMenuInflater();
        inflater.inflate( R.menu.products_menu,menu );
       MenuItem  searcheItem= menu.findItem( R.id.action_search );
      SearchView searchView=(SearchView) searcheItem.getActionView();


        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener( ) {
            @Override
            public boolean onQueryTextSubmit(String s){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                adapter.getFilter().filter( s );
                return false;
            }
        } );
        return true;
    }
}
