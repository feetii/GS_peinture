package com.soufet.gs_peinture;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soufet.gs_peinture.models.Products;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewProduct extends AppCompatActivity {
 ImageView selectIMg;
    Uri imageFile;
    String imageURL;
    DatabaseReference productRef;
    Button add_product;
    EditText nom,description,quantity,seuil,code_de_categorie,code_de_produit,categorie;
    StorageReference productImagesRef;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initialisationOfFields();
        ActivityResultLauncher<Intent> openGalleryResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            imageFile = data.getData();
                            selectIMg.setImageURI(imageFile);
                        }
                    }
                });
        selectIMg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryResult.launch(opengallery());
            }
        });
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFields()){
                    uploadImageToGoogleCloud();
                }
            }
        });

    }
    private void initialisationOfFields(){
       selectIMg =findViewById(R.id.selecImg);
       nom=findViewById(R.id.nom);
       code_de_produit=findViewById(R.id.code_de_produit);
       description=findViewById(R.id.description);
       quantity=findViewById(R.id.quantité);
       seuil=findViewById(R.id.seuil);
       code_de_categorie=findViewById(R.id.code_de_categorie);
       categorie=findViewById(R.id.Catégorie);
       add_product=findViewById(R.id.add_to_list);
        productImagesRef = FirebaseStorage.getInstance().getReference()
                .child("Product images");
        loading = new ProgressDialog(this);
        loading.setTitle("Uploading your product");
        loading.setMessage("Please wait while we are adding your product");

    }
    private String generateID(){

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyyMMdd");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());
        return saveCurrentDate+saveCurrentTime;
    }

    private void savprodinDB(Products products){
        productRef.setValue(products)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loading.dismiss();
                            Toast.makeText(NewProduct.this, "Product added", Toast.LENGTH_SHORT).show();
                        }else{
                            loading.dismiss();
                            Toast.makeText(NewProduct.this, "Erreur", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    public Intent opengallery()
    {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        return i;
    }
    private void uploadImageToGoogleCloud(){
        loading.show();
        String id = generateID();
        productImagesRef.child(id+".jpeg")
                .putFile(imageFile)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){

                            //get download url of the uploaded image
                            productImagesRef.child(id+".jpeg")
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageURL = uri.toString();
                                            productRef= FirebaseDatabase.getInstance(getString(R.string.db_ref)).getReference().child("Products");
                                            productRef = productRef.push(); // Generate a unique key
                                            String id = productRef.getKey(); // Get the generated key from firebase
                                            Products product = new Products(nom.getText().toString(),description.getText().toString(),code_de_categorie.getText().toString(),imageURL,
                                                    code_de_produit.getText().toString(),categorie.getText().toString(),id,Integer.valueOf(quantity.getText().toString())
                                                    ,Integer.valueOf(seuil.getText().toString())

                                            );
                                            savprodinDB(product);

                                        }
                                    });


                        }else{
                            loading.dismiss();
                            Toast.makeText(NewProduct.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private boolean checkFields(){
        if(nom.getText().toString().isEmpty()){
            nom.setError("Please set product name");
            return false;
        }else if (description.getText().toString().isEmpty()){
            description.setError("Please set description");
            return false;
        }else if (quantity.getText().toString().isEmpty()){
            quantity.setError("Please set quantity");
            return false;
        }else if(imageFile==null){
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return false;

        }else if (code_de_categorie.getText().toString().isEmpty()){
            code_de_categorie.setError("Please set code de catigorie");
            return false;
        }else if (code_de_produit.getText().toString().isEmpty()) {
            code_de_produit.setError("Please set code de produit");
            return false;
        }else {return true;}
    }
}
