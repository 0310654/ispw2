package com.example.ispw2.engineering.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventBean {

    private String codice;
    private String nome;
    private String organizzatore;
    private String ente;
    private String tipo_evento; //
    private String localita;//
    private ArrayList<String> settore;
    //numero posti disponibili
    private ArrayList<Integer> disponibilita_settore;
    private ArrayList<Double> prezzo_settore;//
    private LocalDateTime data_evento;//
    private ArrayList<Integer>  num_posti_settore;
    private String descrizione;

    public EventBean(String codice, String nome, String organizzatore, String ente, String tipo_evento, String localita, ArrayList<String> settore, ArrayList<Integer> disponibilita_settore, ArrayList<Double> prezzo_settore, LocalDateTime data_evento, ArrayList<Integer> num_posti_settore, String descrizione ) {
        if(settore.size()!=disponibilita_settore.size()
                || disponibilita_settore.size() != prezzo_settore.size()
                || prezzo_settore.size() != num_posti_settore.size() ) {
            throw new IllegalArgumentException("Le liste dei settori, prezzi, posti e disponibilità devono avere la stessa dimensione!");
        }
        else{
            this.codice = codice;
            this.nome = nome;
            this.organizzatore = organizzatore;
            this.ente = ente;
            this.tipo_evento = tipo_evento;
            this.localita = localita;
            this.settore = settore;
            this.disponibilita_settore = disponibilita_settore;
            this.prezzo_settore = prezzo_settore;
            this.data_evento = data_evento;
            this.num_posti_settore = num_posti_settore;
            this.descrizione = descrizione;
        }
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getOrganizzatore() {
        return organizzatore;
    }

    public String getEnte() {
        return ente;
    }

    public String getTipo_evento() {
        return tipo_evento;
    }

    public String getLocalita() {
        return localita;
    }

    public ArrayList<String> getSettore() {
        return settore;
    }

    public ArrayList<Integer> getDisponibilita_settore() {
        return disponibilita_settore;
    }

    public ArrayList<Double> getPrezzo_settore() {
        return prezzo_settore;
    }

    public LocalDateTime getData_evento() {
        return data_evento;
    }

    public ArrayList<Integer> getNum_posti_settore() {
        return num_posti_settore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String toString() {

        /*System.out.println(settore.size());
        for(String str : settore) {
            System.out.println(str);
        }
        System.out.println(disponibilita_settore.size());
        for(double d : disponibilita_settore) {
            System.out.println(d);
        }
        System.out.println(prezzo_settore.size());
        for(double d : prezzo_settore) {
            System.out.println(d);
        }
        System.out.println(num_posti_settore.size());
        for(double d : num_posti_settore) {
            System.out.println(d);
        }*/


        StringBuilder sb = new StringBuilder();
        sb.append("Codice: " + codice+ " | ");
        sb.append("Nome evento: " + nome + " | ");
        sb.append("Organizzatore: " + organizzatore + " | ");
        sb.append("Ente: " + ente + " | ");
        sb.append("Tipo evento: " + tipo_evento + " | ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        sb.append("Data e ora: " + data_evento.format(formatter) + " | ");
        sb.append("Settori: { " );
        for(int i = 0; i < settore.size(); i++) {
            sb.append("Nome settore: " + settore.get(i) + " ; ");
            sb.append("Prezzo settore: " + prezzo_settore.get(i) + " ; ");
            sb.append("Posti settore: " + num_posti_settore.get(i) + " ; ");
            sb.append("Posti disponibili: " + disponibilita_settore.get(i) + " ; ");
        }
        sb.append("Descrizione evento: " + descrizione + " | ");
        sb.append("} ");
        return sb.toString();
    }
}
