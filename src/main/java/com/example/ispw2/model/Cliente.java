package com.example.ispw2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends User {

    private LocalDate data_registrazione;
    private Prenotazione prenotazioni_pendenti;

    /*private List<Prenotazione> prenotazioni_confermate = new ArrayList<>();
    private List<Prenotazione> prenotazioni_annullate = new ArrayList<>();
    private List<Prenotazione> prenotazioni_scadute = new ArrayList<>();*/


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


    /*public List<Prenotazione getPrenotazioni_confermate() {
        return prenotazioni_confermate;
    }

    public void setPrenotazioni_confermate(Prenotazione prenotazione_confermata) {
        this.prenotazioni_confermate.add(prenotazione_confermata);
    }

    public List<Prenotazione> getPrenotazioni_annullate() {
        return prenotazioni_annullate;
    }

    public void setPrenotazioni_annullate(Prenotazione prenotazione_annullata) {
        this.prenotazioni_annullate.add(prenotazione_annullata);
    }

    public List<Prenotazione> getPrenotazioni_scadute() {
        return prenotazioni_scadute;
    }

    public void setPrenotazioni_scadute(Prenotazione prenotazione_scaduta) {
        this.prenotazioni_scadute.add(prenotazione_scaduta);
    }*/



    /*creazione user in memory mode
    public Costumer(String email, String password, String name, String surname, SupportedUserTypes type, List<Borrow> pendingBorrows) {
        super(email, name, surname, password, type);
        this.membershipDate = LocalDate.now();
        this.membershipStatus = true;
        this.pendingBorrows = pendingBorrows;
    }*/

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
