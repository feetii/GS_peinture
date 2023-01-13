package com.soufet.gs_peinture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.soufet.gs_peinture.models.Products;
import com.soufet.gs_peinture.models.Supplier;

public class AddSuplierActivity extends AppCompatActivity {



    DatabaseReference productRef;
    Button add_four;
    EditText     nom,
            phone,
            email,

                address;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_suplier );
        initialisationOfFields();
        add_four.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view){
                loading.show();
                productRef= FirebaseDatabase.getInstance(getString(R.string.db_ref)).getReference().child("Suppliers");
                productRef = productRef.push(); // Generate a unique key
                String id = productRef.getKey(); // Get the generated key from firebase
                Supplier supplier = new Supplier(nom.getText().toString(),address.getText().toString(),phone.getText().toString(),
                        email.getText().toString(),id);


                savprodinDB(supplier);
            }
        } );
    }
    private void savprodinDB(Supplier supplier){
        productRef.setValue(supplier)
                .addOnCompleteListener(new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull Task <Void> task) {
                        if(task.isSuccessful()){
                            loading.dismiss();
                            Toast.makeText(AddSuplierActivity.this, "Supplier added", Toast.LENGTH_SHORT).show();

                            finish();
                        }else{
                            loading.dismiss();
                            Toast.makeText(AddSuplierActivity.this, "Erreur", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    private void initialisationOfFields(){

        nom=findViewById(R.id.nom);
        address=findViewById(R.id.address);
       phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        add_four=findViewById(R.id.add_four);

        loading = new ProgressDialog(this);
        loading.setTitle("Uploading your fournisseur");
        loading.setMessage("Please wait while we are adding your fournisseur");

    }
}