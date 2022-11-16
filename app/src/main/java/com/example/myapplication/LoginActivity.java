package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private LoginController controller;

    private Button registraAccountButton;
    private Button accediButton;
    //private Button accediFacebookButton;
    private Button accediGoogleButton;

    private EditText emailEditText, passwordEditText;

    private String email, password;
    //private LoginButton accediFacebookButton;
    //private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ottieniRiferimenti();

        configuraComponenti();

        controller = new LoginController(this);


    }

    private void ottieniRiferimenti(){
        registraAccountButton = findViewById(R.id.registraAccountButton);
        accediButton = findViewById(R.id.buttonAccedi);
        //accediFacebookButton = findViewById(R.id.Login_Facebook_Button);
        accediGoogleButton = findViewById(R.id.Login_Google_Button);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
    }

    private void configuraComponenti(){
        configuraAccediButton();
        configuraRegistraButton();
        //configuraAccediFacebookButton();
        configuraAccediGoogleButton();
    }

    private void configuraAccediButton(){
        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accedi();
            }
        });
    }
/*
    private void configuraAccediFacebookButton(){
        accediFacebookButton.setReadPermissions("email");

        accediFacebookButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                //controller.loginFacebook();


                Log.i("PAtzcycbzdkcjbsdjkbvdv", "facebook token: " + AccessToken.getCurrentAccessToken().getToken());


            }
        });

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("Ciaooooo Success Token ",loginResult.getAccessToken().getToken());

                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(getApplicationContext(),"eu-central-1:86eb4f4c-6823-4393-a8a3-65bdf5509c18", Regions.EU_CENTRAL_1);
                Map<String, String> logins = new HashMap<String, String>();
                logins.put("graph.facebook.com", AccessToken.getCurrentAccessToken().getToken());
                credentialsProvider.setLogins(logins);

                Log.i("ID Utente FB",loginResult.getAccessToken().getUserId());

                controller.loginFacebook();

                federateWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
            }
        });


    }

    private void federateWithFacebook(AccessToken accessToken) {


    }
    */


    private void configuraAccediGoogleButton(){
        accediGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.loginGoogle();
            }
        });
    }

    private void configuraRegistraButton(){
        registraAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.apriRegistrazioneAccount();
            }
        });
    }

    private void accedi(){
        if (!campiVuoti()){
            ottieniDati();
            //controller.loginWithFirebase();
           // controller.login(email, password);
        } else {
            Toast.makeText(this, "Campi Richiesti Vuoti. Riempili tutti o registra un account.", Toast.LENGTH_LONG).show();
        }
    }

    private void ottieniDati(){
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
    }

    private boolean campiVuoti(){
        if (TextUtils.isEmpty(emailEditText.getText()))
            return true;
        if (TextUtils.isEmpty(passwordEditText.getText()))
            return true;
        return false;
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

     */
}
