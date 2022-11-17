package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginController loginController = new LoginController(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                loginController.apriLoginActivity();

            }
        });

        /*
        FirebaseDatabase.getInstance("https://fir-proj-ae9c9-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot = task.getResult().child("users");

                        Utente utente1 =  dataSnapshot.getValue(Utente.class);

                        System.out.println(utente1.toString());

                    }
                });
*/
        FirebaseDatabase database = FirebaseDatabase.getInstance(("https://fir-proj-ae9c9-default-rtdb.europe-west1.firebasedatabase.app/"));

        DatabaseReference databaseReference = database.getReference("users").child(currentUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utente utente = snapshot.getValue(Utente.class);
                if(utente != null){


                    //Vengono salvati tutti i dati utente
                    System.out.println("MainAcr"+utente.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}