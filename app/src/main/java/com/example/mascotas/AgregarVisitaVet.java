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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AgregarVisitaVet extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference spinnerRef;
    EditText nombreVet, sucursal, fechaVisita, observacion;
    Spinner spnNombreMascota, spnTipoAtencion;
    ArrayList spinnerList, idList;
    ArrayAdapter<String> adapter;


    //menu
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
        setContentView(R.layout.activity_agregar_visita_vet);
        nombreVet = findViewById(R.id.nombreVeterinario);
        sucursal = findViewById(R.id.nombreConsulta);
        fechaVisita = findViewById(R.id.fechaVet);
        observacion = findViewById(R.id.vetObservacion);
        spnNombreMascota = findViewById(R.id.spinnerMascotvET);
        spnTipoAtencion = findViewById(R.id.spinnerAtencion);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        spinnerRef = db.collection("usuarios").document(uid).collection("mascotas");
        idList = new ArrayList<>();
        spinnerList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(AgregarVisitaVet.this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNombreMascota.setAdapter(adapter);
        //rellenar
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

        fechaVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
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
        fechaVisita.setText(fecha);

    }
}