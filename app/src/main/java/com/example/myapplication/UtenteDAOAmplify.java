package com.example.myapplication;

import android.util.Log;
import android.widget.Toast;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class UtenteDAOAmplify implements UtenteDAO {
    private LoginActivity loginActivity;
    private RegistrazioneActivity registrazioneActivity;

    private LoginController loginController;

    public UtenteDAOAmplify(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    public UtenteDAOAmplify(RegistrazioneActivity activity) {
        this.registrazioneActivity = activity;
    }

}
