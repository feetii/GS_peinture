package com.soufet.gs_peinture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.eap.EapSessionConfig;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
EditText Email,Password;
CardView Login;
FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initialisationOfFields();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFields()){
                    signIn();

                }
            }
        });

    }
    private boolean checkFields(){
        if(Email.getText().toString().isEmpty()){
            Email.setError("please fill the email");
            return false;
        }else if (Password.getText().toString().isEmpty()){
            Password.setError("Please fill the password");
            return false;
        }else {
            return true;
        }
    }
    private void signIn(){
        String my_email =Email.getText().toString();
        String my_password = Password.getText().toString();
        mauth.signInWithEmailAndPassword(my_email,my_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }else{

                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void initialisationOfFields(){
        Email =findViewById(R.id.Email);
        Password=findViewById(R.id.Password);
        Login=findViewById(R.id.Login);
        mauth = FirebaseAuth.getInstance();
    }
}