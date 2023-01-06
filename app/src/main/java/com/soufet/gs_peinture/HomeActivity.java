package com.soufet.gs_peinture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button liste ,
    ajout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();//Ocultar ActivityBar anterior
        initialisationOfFields();
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,Listeofproducts.class);
                startActivity(i);
            }
        });
        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,NewProduct.class);
                startActivity(i);
            }
        });
    }

    private void initialisationOfFields(){
        liste=findViewById(R.id.liste_button);
        ajout=findViewById(R.id.ajout_button);

    }
}