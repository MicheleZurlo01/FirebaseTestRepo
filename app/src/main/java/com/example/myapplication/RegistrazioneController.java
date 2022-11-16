package com.example.myapplication;

import android.content.Intent;


public class RegistrazioneController {
    private RegistrazioneActivity activity;

    private UtenteDAO utenteDAO;


    public RegistrazioneController(RegistrazioneActivity activity) {
        this.activity = activity;

        utenteDAO = new UtenteDAOAmplify(activity);
    }

    public void apriSchermataPrincipale(){
        Intent mappaIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(mappaIntent);
    }



}
