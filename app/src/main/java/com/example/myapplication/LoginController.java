package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;;

import java.util.HashMap;
import java.util.Map;


public class LoginController {

    private UtenteDAO utenteDAO;
    private LoginActivity activity;


    public LoginController(LoginActivity loginActivity) {
        this.activity = loginActivity;

        utenteDAO = new UtenteDAOAmplify(activity);
    }

    public void apriRegistrazioneAccount(){
        Intent registrazioneIntent = new Intent(activity, RegistrazioneActivity.class);
        activity.startActivity(registrazioneIntent);
    }



    public void loginFacebook(){

        //codice per il sign-in con facebook
        // AccessToken accessToken = AccessToken.getCurrentAccessToken();
        // boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        activity.startActivity(new Intent(activity,MainActivity.class));

    }

    public void loginGoogle(){
        //codice per il sign-in con google
    }

    public void apriMainActivity() {
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        activity.startActivity(intent);
        Log.i(this.getClass().toString(), "Login Account: ");
    }
}
