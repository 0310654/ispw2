package com.example.ispw2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Prenotazione {
    private String nome_evento;
    private String cod_prenotazione;
    private String nome;
    private String cognome;
    private LocalDateTime data_evento;
    private LocalDateTime data_prenotazione;
    private String stato_prenotazione;

    public Prenotazione(String nome_evento, String cod_prenotazione, String nome, String cognome,LocalDateTime data_evento, LocalDateTime data_prenotazione, String stato_prenotazione) {
        this.nome_evento = nome_evento;
        this.cod_prenotazione = cod_prenotazione;
        this.nome = nome;
        this.cognome = cognome;
        this.data_evento = data_evento;
        this.data_prenotazione = data_prenotazione;
        this.stato_prenotazione = stato_prenotazione;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome evento: " + nome_evento+ " | ");
        sb.append("Codice prenotazione: " + cod_prenotazione+ " | ");
        sb.append("Nome: " + nome + " | ");
        sb.append("Cognome: " + cognome + " | ");
        sb.append("Stato prenotazione: " + stato_prenotazione + " | ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        sb.append("Data e ora: " + data_prenotazione.format(formatter) + " | ");
        return sb.toString();
    }
    public String getNome_evento() {
        return nome_evento;
    }

    public void setNome_evento(String nome_evento) {
        this.nome_evento = nome_evento;
    }

    public String getCod_prenotazione() {
        return cod_prenotazione;
    }

    public void setCod_prenotazione(String cod_prenotazione) {
        this.cod_prenotazione = cod_prenotazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDateTime getData_prenotazione() {
        return data_prenotazione;
    }

    public void setData_prenotazione(LocalDateTime data_prenotazione) {
        this.data_prenotazione = data_prenotazione;
    }

    public String getStato_prenotazione() {
        return stato_prenotazione;
    }

    public LocalDateTime getData_evento() {
        return data_evento;
    }

    public void setData_evento(LocalDateTime data_evento) {
        this.data_evento = data_evento;
    }
    public void setStato_prenotazione(String stato_prenotazione) {
        this.stato_prenotazione = stato_prenotazione;
    }
}
