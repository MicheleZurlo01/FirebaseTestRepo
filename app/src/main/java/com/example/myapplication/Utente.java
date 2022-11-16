package com.example.myapplication;

import java.util.Objects;

public class Utente {
    public String name, cognome, email, username, dataNascita, sesso, password;
    private int id;

    public Utente() {

    }

    public Utente(String nome, String cognome, String email, String username, String dataNascita, String sesso, String password) {
        this.name = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.password = password;
    }

    public Utente(String nome) {
        this.name = nome;
    }

    public Utente(int id, String nome) {
        this.id = id;
        this.name = nome;
    }

    public Utente(String nome, String cognome, String email, String username, String dataNascita, String sesso) {
        this.name = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
    }

    public Utente(String email, String password){
        this.email = email;
        this.password = password;
    }

    //per test


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name + " " + cognome + "\nEmail: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return email.equals(utente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
