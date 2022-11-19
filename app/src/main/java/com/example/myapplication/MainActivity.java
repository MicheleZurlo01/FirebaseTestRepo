package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    @Override
    protected void onStart() {
        super.onStart();

        //
        FirebaseDatabase database = FirebaseDatabase.getInstance(("https://fir-proj-ae9c9-default-rtdb.europe-west1.firebasedatabase.app/"));

        DatabaseReference databaseReference = database.getReference("users").child(currentUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utente utente = snapshot.getValue(Utente.class);
                if(utente != null){

                    //Login with Firebase
                    //Vengono salvati tutti i dati utente
                    System.out.println("MainAcr"+utente.toString());
                }else
                {
                    //login with google
                    System.out.println("Login google info:" + currentUser.getDisplayName()+ currentUser.getPhotoUrl());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginController loginController = new LoginController(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        currentUser = auth.getCurrentUser();

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();

                loginController.apriLoginActivity();

            }
        });



    }
}