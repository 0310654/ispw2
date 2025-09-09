package com.example.ispw2.engineering.bean;

import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class SelectedBean {

    private String evento;
    private String localita;
    private LocalDateTime data;
    private String prezzo;

    public SelectedBean(String evento, String localita, LocalDateTime data, String prezzo) {
        this.evento = evento;
        this.localita = localita;
        this.data = data;
        this.prezzo = prezzo;
    }

    public String getTipoEvento() {
        return evento;
    }

    public String getLocalita() {
        return localita;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getPrezzo() {
        return prezzo;
    }
}
