package com.example.mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mascotas.clases.Animal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarMascotas extends AppCompatActivity {

EditText nombreMascota, razaMascota, birthyear, alimento;

Button botonAgregar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addMascota:

                Intent intent = new Intent(this, AgregarMascotas.class);
                startActivity(intent);
                // finish();
                return true;
            case R.id.addVacuna:

                Intent intent1 = new Intent(this, AgregarVacunas.class);
                startActivity(intent1);

                return true;
            case R.id.addVisita:
                Intent intent3 = new Intent(this, AgregarVisitaVet.class);
                startActivity(intent3);

                return true;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(this, Login.class);
                startActivity(intent2);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascotas);
        nombreMascota = findViewById(R.id.addMascotaName);
        razaMascota = findViewById(R.id.addRazaMascota);
        birthyear = findViewById(R.id.addbirthyear);
        alimento = findViewById(R.id.addAlimento);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerEspecie);
        botonAgregar = findViewById(R.id.bt_addMascota);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
         R.array.especies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrePet, anoNac, alimentoPet, razaPet, especiePet;
                nombrePet= String.valueOf(nombreMascota.getText());
                anoNac = String.valueOf(birthyear.getText());
                alimentoPet= String.valueOf(alimento.getText());
                razaPet= String.valueOf(razaMascota.getText());
                especiePet = spinner.getSelectedItem().toString();

                if(nombrePet.isEmpty()||anoNac.isEmpty()||alimentoPet.isEmpty()||razaPet.isEmpty()){
                    Toast.makeText(AgregarMascotas.this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else{

                    Map<String, Object> docData = new HashMap<>();
                    docData.put("nombre", nombrePet);
                    docData.put("fecha_nacimiento", anoNac);
                    docData.put("alimento", alimentoPet);
                    docData.put("raza", razaPet);
                    docData.put("especie", especiePet);
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                 db.collection("usuarios").document(uid).collection("mascotas")
                         .add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AgregarMascotas.this, "Guardado correctamente", Toast.LENGTH_SHORT);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AgregarMascotas.this, "Error al ingresar a la db", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }


        });





    }
}