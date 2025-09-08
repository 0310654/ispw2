package com.example.ispw2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends User {

    private LocalDate data_registrazione;
    private Prenotazione prenotazioni_pendenti;
    private String pending;

    public Cliente(String email, String password, String name, String surname, LocalDate data_registrazione, Prenotazione prenotazioni_pendenti) {
        super(email, name, surname, password, "utente registrato");
        if (data_registrazione == null) {
            data_registrazione = LocalDate.now();
        }
        this.data_registrazione = data_registrazione;
        this.prenotazioni_pendenti = prenotazioni_pendenti;
    }

    public LocalDate getdata_registrazione() {
        return data_registrazione;
    }

    public void setdata_registrazione(LocalDate data_registrazione) {
        this.data_registrazione = data_registrazione;
    }

    public Prenotazione getPrenotazioni_pendenti() {
        return prenotazioni_pendenti;
    }

    public void setPrenotazionePendente(Prenotazione prenotazione) {this.prenotazioni_pendenti = prenotazione;}



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email: " + getEmail()+ " | ");
        sb.append("Nome: " + getName() + " | ");
        sb.append("Cognome: " + getSurname() + " | ");
        sb.append("Tipo user: " + getType() + " | ");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        sb.append("Data: " + data_registrazione.format(dtf) + " | ");

        return sb.toString();
    }

}
