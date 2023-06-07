package com.example.mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AgregarVacunas extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Spinner mascotuser;
    EditText nombreVac, fechaVac;
    Button btnAddVac;
    CollectionReference spinnerRef;
    ArrayList<String> spinnerList, idList;
    ArrayAdapter<String> adapter;

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

            case R.id.Inicio:
                Intent intent4 = new Intent(this, MainActivity.class);
                startActivity(intent4);
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agregar_vacunas);
        mascotuser = findViewById(R.id.spinnerMascot);
        nombreVac = findViewById(R.id.nombreVacuna);
        btnAddVac = findViewById(R.id.btn_agregarVacuna);
        fechaVac = findViewById(R.id.fechaVacuna);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        spinnerRef = db.collection("usuarios").document(uid).collection("mascotas");
        idList = new ArrayList<>();
        spinnerList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(AgregarVacunas.this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mascotuser.setAdapter(adapter);

        spinnerRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String nombremascota = document.getString("nombre");
                        String iudmascota = document.getId();
                       spinnerList.add(nombremascota);
                       idList.add(iudmascota);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        fechaVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //AGREGAR VACUNAS
        btnAddVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fecha = fechaVac.getText().toString();
                String mascota= mascotuser.getSelectedItem().toString();
                String idMascota = idList.get(mascotuser.getSelectedItemPosition());
                String nombreVacuna= nombreVac.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(fecha.isEmpty()||mascota.isEmpty()|| nombreVacuna.isEmpty()){
                    Toast.makeText(AgregarVacunas.this, "Por favor, Rellene todos los campos", Toast.LENGTH_LONG).show();
                }else {
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("nombreVacuna", nombreVacuna);
                    docData.put("fechaInoculacion", fecha);
                    db.collection("usuarios").document(uid).collection("mascotas").document(idMascota)
                            .collection("Vacunas").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AgregarVacunas.this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                                    nombreVac.getText().clear();
                                    fechaVac.getText().clear();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AgregarVacunas.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                                }
                            });


                }

            }
        });


    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String fecha= DateFormat.getDateInstance().format(c.getTime());
        fechaVac.setText(fecha);

    }
}