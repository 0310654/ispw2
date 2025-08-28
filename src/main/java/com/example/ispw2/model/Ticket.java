package com.example.ispw2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {

    private String cod_ticket;
    private String cod_evento;
    private String nome;
    private String cognome;
    private String settore;
    private String posto;
    private LocalDateTime data;

    public Ticket(String cod_ticket, String cod_evento, String nome, String cognome, String settore, String posto, LocalDateTime data) {
        this.cod_ticket = cod_ticket;
        this.cod_evento = cod_evento;
        this.nome = nome;
        this.cognome = cognome;
        this.settore = settore;
        this.posto = posto;
        this.data = data;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codice biglietto: " + cod_ticket+ " | ");
        sb.append("Codice evento: " + cod_evento + " | ");
        sb.append("Nome: " + nome + " | ");
        sb.append("Cognome: " + cognome + " | ");
        sb.append("Settore: " + settore + " | ");
        sb.append("Posto: " + posto + " | ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        sb.append("Data e ora: " + data.format(formatter) + " | ");
        return sb.toString();
    }

    public String getCod_ticket() {
        return cod_ticket;
    }

    public void setCod_ticket(String cod_ticket) {
        this.cod_ticket = cod_ticket;
    }

    public String getCod_evento() {
        return cod_evento;
    }

    public void setCod_evento(String cod_evento) {
        this.cod_evento = cod_evento;
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

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

}
