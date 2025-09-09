package com.example.ispw2.view.gui.other;

import com.example.ispw2.model.Evento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;

public class SchedaEventoController {


    private Label nomeEventoLabel;
    private Label infoEventoLabel;

    private Evento evento;

    public void setEvento(Evento evento) {
        this.evento = evento;
        aggiornaUI();
    }

    private void aggiornaUI() {
        if (evento == null) return;

        // Popola i campi con i dati dell'evento
        nomeEventoLabel.setText(evento.getNome());
        String infoevento = "Descrizione:\n\t" + evento.getDescrizione() + "\n" +
                "\nLocalità:\n\t" + evento.getLocalita() + "\n" +
                "\nCodice:\n\t" + evento.getCodice() + "\n";
        infoEventoLabel.setText(infoevento);
        infoEventoLabel.setWrapText(true);
    }

    public void prenota(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {
    }
}
