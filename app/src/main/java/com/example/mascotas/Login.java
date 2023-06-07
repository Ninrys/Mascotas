package com.example.mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText correoLogin, passwordLogin;
    Button botonLogin;
    TextView registrar;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        correoLogin= findViewById(R.id.correoLogin);
        passwordLogin=findViewById(R.id.passLogin);
        botonLogin=findViewById(R.id.botonLogin);
        registrar=findViewById(R.id.signinTV);
        mAuth = FirebaseAuth.getInstance();

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registrar.class);
                startActivity(intent);
                finish();
            }
        });

       botonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email, password;
               email= String.valueOf(correoLogin.getText());
               password= String.valueOf(passwordLogin.getText());

               if(TextUtils.isEmpty(email)){
                   Toast.makeText(Login.this, "Por favor ingrese un correo", Toast.LENGTH_LONG).show();
                   return;
               }

               if(TextUtils.isEmpty(password)){
                   Toast.makeText(Login.this, "Por favor ingrese contraseña", Toast.LENGTH_LONG).show();
                   return;

               }

               mAuth.signInWithEmailAndPassword(email, password)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   // Sign in success, update UI with the signed-in user's informatio
                                   FirebaseUser user = mAuth.getCurrentUser();
                                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                   startActivity(intent);
                                   finish();

                               } else {
                                   // If sign in fails, display a message to the user.

                                   Toast.makeText(Login.this, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();

                               }
                           }
                       });
           }
       });





    }
}