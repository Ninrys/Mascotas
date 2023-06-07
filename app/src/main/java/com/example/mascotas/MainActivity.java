package com.example.mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);


    }
}