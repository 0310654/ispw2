package com.example.ispw2.engineering.bean;

import com.example.ispw2.model.Prenotazione;

import java.time.LocalDate;

public class RegisterBean{
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate data_registrazione;
    private Prenotazione prenotazione_pendente;

    public RegisterBean(String name, String surname, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    //per lettura da JSON
    public RegisterBean(String name, String surname, String email, String password, LocalDate data_registrazione, Prenotazione prenotazione_pendente) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.data_registrazione = data_registrazione;
        this.prenotazione_pendente = prenotazione_pendente;
    }

    public LocalDate getData_registrazione() {
        return data_registrazione;
    }

    public void setData_registrazione(LocalDate data_registrazione) {
        this.data_registrazione = data_registrazione;
    }


    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}
    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}

    public Prenotazione getPrenotazione_pendente() {
        return prenotazione_pendente;
    }

    public void setPrenotazione_pendente(Prenotazione prenotazione_pendente) {
        this.prenotazione_pendente = prenotazione_pendente;
    }


}
