package com.example.ispw2.view.gui.other;

import com.example.ispw2.model.Evento;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SchedaEventoController {

    @FXML
    private Label nomeEventoLabel;
    private Evento evento;


    public void setEvento(Evento evento) {
        this.evento = evento;

        // Popolo la schermata con i dati dell'evento
        nomeEventoLabel.setText(evento.getNome());
    }
}
