package com.example.mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {
TextInputEditText correoreg, passwordreg;
Button botonregistro;
FirebaseAuth mAuth;
String userID;
FirebaseFirestore db;
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
        setContentView(R.layout.activity_registrar);
        correoreg = findViewById(R.id.emailregis);
        passwordreg= findViewById(R.id.passregis);
        botonregistro = findViewById(R.id.botonregis);
        mAuth= FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
               email= String.valueOf(correoreg.getText());
               password= String.valueOf(passwordreg.getText());

               if(TextUtils.isEmpty(email)){
                   Toast.makeText(Registrar.this, "Por favor ingrese un correo", Toast.LENGTH_LONG).show();
                   return;
               }

               if(TextUtils.isEmpty(password)){
                   Toast.makeText(Registrar.this, "Por favor ingrese contraseña", Toast.LENGTH_LONG).show();
                   return;

               }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(Registrar.this, "Cuenta Creada :D",
                                            Toast.LENGTH_SHORT).show();
                                    // Sign in success, update UI with the signed-in user's information
                                    userID= mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("usuarios").document(userID);
                                    Map<String, Object> docData = new HashMap<>();
                                    docData.put("nombre", "Nombre");
                                    documentReference.set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });


                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Registrar.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }
}