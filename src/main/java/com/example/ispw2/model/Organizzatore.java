package com.example.ispw2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Organizzatore extends User {
    private LocalDate data_reg;
    private ArrayList<Evento> iMieiEventi;



    public Organizzatore(String email, String password, String name, String surname, LocalDate data_reg, ArrayList<Evento> iMieiEventi) {
        super(email, name, surname, password, "organizzatore");
        this.data_reg = LocalDate.now();
        this.iMieiEventi = iMieiEventi;

    }

    public ArrayList<Evento> getiMieiEventi() {
        return iMieiEventi;
    }
    public void addiMieiEventi(Evento evento) {
        iMieiEventi.add(evento);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email: " + getEmail()+ " | ");
        sb.append("Nome: " + getName() + " | ");
        sb.append("Cognome: " + getSurname() + " | ");
        sb.append("Tipo user: " + getType() + " | ");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        sb.append("Data: " + data_reg.format(dtf) + " | ");

        return sb.toString();
    }

    /*//creazione organizzatore per in memory mode
    public Organizzatore(String email, String password, String name, String surname, String type) {
        super(email, name, surname, password, type);
        this.data_reg = LocalDate.now();
        this.type = SupportedRoleTypes.SUPERVISOR;
    }*/
}
