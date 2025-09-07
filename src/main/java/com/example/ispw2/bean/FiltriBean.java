package com.example.ispw2.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FiltriBean {

    private ArrayList<String> eventi;
    private ArrayList<String> localita;
    private ArrayList<String> prezzo;

    public ArrayList<String> getEventi() {
        return eventi;
    }
    public void addEvento(String evento) {
        this.eventi.add(evento);
    }

    public ArrayList<String> getLocalita() {
        return localita;
    }
    public void addLocalita(String localita) {
        this.localita.add(localita);
    }

    public ArrayList<String> getPrezzo() {
        return prezzo;
    }
    public void addPrezzo(String prezzo) {
        this.prezzo.add(prezzo);
    }

    public FiltriBean() {
        eventi = new ArrayList<>();
        this.localita = new ArrayList<>();
        prezzo = new ArrayList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Eventi:\n ");
        for (String s : eventi) {
            sb.append("\t" + s + "\n");
        }
        sb.append("Località:\n");
        for (String s : localita) {
            sb.append("\t" + s + "\n");
        }
        sb.append("Prezzo:\n " );
        for (String s : prezzo) {
            sb.append("\t" + s + "\n");
        }
        return sb.toString();
    }
}
