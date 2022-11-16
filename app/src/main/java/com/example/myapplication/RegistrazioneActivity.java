package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegistrazioneActivity extends AppCompatActivity {
    private RegistrazioneController controller;

    private Button registraButton;
    private Button caricaFotoButton;
    private ImageView fotoAvatar;
    private EditText nomeEditText;
    private EditText cognomeEditText;
    private RadioGroup sessoRadioGroup;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText dataNascitaDateEditText;
    private RadioButton sessoButton;

    private EditText codiceEditText;

    private String nome, cognome, email, username, password, dataNascita, sesso;

    private AlertDialog.Builder confermaDialog;

    private DatePickerDialog datePickerDialog;
    private int giornoCorrente = 1;
    private int meseCorrente = 1;
    private int annoCorrente = 1900;
    private Calendar calendario;

    private final int risultatoIntentCaricaFoto = 200;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        firebaseAuth = FirebaseAuth.getInstance();

        ottieniRiferimentiComponenti();
       // inizializzaVariabili();
        configuraComponenti();

        controller = new RegistrazioneController(this);
    }

    private void inizializzaVariabili() {
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == risultatoIntentCaricaFoto && data != null){
            Uri fotoUri = data.getData();
            fotoAvatar.setImageURI(fotoUri);

            String accessKey = "AKIAUP5XLUCD5FM6NUOG";
            String secretKey="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";
            String bucketName = "natour215fa910556a6e48489a03b78a2759a592152000-dev";
            String keyName = "NaTourFile.jpg";


        }
    }

    private void ottieniRiferimentiComponenti(){
        dataNascitaDateEditText = findViewById(R.id.editTextDate);
        registraButton = findViewById(R.id.registerButton);
        nomeEditText = findViewById(R.id.editTextNome);
        cognomeEditText = findViewById(R.id.editTextCognome);
        sessoRadioGroup = findViewById(R.id.radioGroupSesso);
        emailEditText = findViewById(R.id.editTextEmail);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        caricaFotoButton = findViewById(R.id.caricaFotoButton);
        fotoAvatar = findViewById(R.id.Avatar);

    }

    private void configuraComponenti(){
        fotoAvatar.setClipToOutline(true);
        configuraRegistraButton();
        configuraDatePicker();
        configuraDateEditText();
       // configuraConfermaDialog();
        configuraCaricaFotoButton();


    }

    private void configuraRegistraButton() {


        registraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!campiVuoti()){
                    //ottieniDati();

                    //schermata di conferma se non ci sono eccezioni

                    registerUser();




                } else {
                    Toast.makeText(view.getContext(), "Campi Richiesti Vuoti. Riempili tutti.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerUser() {

        ottieniDati();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        // Save data in firebase DB

                            Utente utente = new Utente(nome,cognome,email,username,dataNascita,sesso,password);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(utente).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            controller.apriSchermataPrincipale();
                                        }
                                    });

                        }else{
                            Toast.makeText(getApplicationContext(),"Auth Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void configuraCaricaFotoButton(){
        caricaFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caricaFoto();
            }
        });
    }

    private void caricaFoto(){
        Intent caricaFotoIntent = new Intent(Intent.ACTION_PICK);
        Log.i("Carica foto",caricaFotoIntent.toString());
        caricaFotoIntent.setType("image/*");
        startActivityForResult(caricaFotoIntent, risultatoIntentCaricaFoto);
        Log.i("Carica foto",caricaFotoIntent.toString());
    }

    private void configuraDatePicker(){
        inizializzaDataPicker();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int anno, int mese, int giorno) {
                SimpleDateFormat dataFormatter = new SimpleDateFormat("dd/MM/yyyy");
                calendario.set(anno, mese, giorno);
                dataNascitaDateEditText.setText(dataFormatter.format(calendario.getTime()));
            }
        }, annoCorrente, meseCorrente, giornoCorrente);
    }

    private void inizializzaDataPicker(){
        calendario = Calendar.getInstance();
        annoCorrente = calendario.get(Calendar.YEAR);
        meseCorrente = calendario.get(Calendar.MONTH);
        giornoCorrente = calendario.get(Calendar.DAY_OF_MONTH);
    }

    private void configuraDateEditText(){
        dataNascitaDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    private void ottieniDati(){
        nome = nomeEditText.getText().toString();
        cognome = cognomeEditText.getText().toString();
        email = emailEditText.getText().toString();
        dataNascita = String.valueOf(calendario.getTime());
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        sessoButton = findViewById(sessoRadioGroup.getCheckedRadioButtonId());
        sesso = sessoButton.getText().toString();

        Log.i("RegistrazioneUI","Raccolti dati:" +
                "\nNome:" + nome +
                "\nCognome:" + cognome +
                "\nEmail:" + email +
                "\nUsername:" + username +
                "\nPassword:" + password +
                "\nData di nascita:" + dataNascita +
                "\nSesso:" + sesso);
    }

    private boolean campiVuoti(){
        if (TextUtils.isEmpty(nomeEditText.getText()))
            return true;
        if (TextUtils.isEmpty(cognomeEditText.getText()))
            return true;
        if (TextUtils.isEmpty(emailEditText.getText()))
            return true;
        if (TextUtils.isEmpty(passwordEditText.getText()))
            return true;
        if (TextUtils.isEmpty(usernameEditText.getText()))
            return true;
        if (TextUtils.isEmpty(dataNascitaDateEditText.getText()))
            return true;
        return false;
    }
}