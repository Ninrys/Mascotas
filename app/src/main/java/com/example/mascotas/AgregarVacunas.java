package com.example.mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class AgregarVacunas extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Spinner mascotuser;
    EditText nombreVac, fechaVac;
    Button btnAddVac;
    CollectionReference spinnerRef;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vacunas);
        mascotuser = findViewById(R.id.spinnerMascot);
        nombreVac = findViewById(R.id.nombreVacuna);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        spinnerRef = db.collection("usuarios").document(uid).collection("mascotas");

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
                       spinnerList.add(nombremascota);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}