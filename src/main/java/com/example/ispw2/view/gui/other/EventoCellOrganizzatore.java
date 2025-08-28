package com.example.ispw2.view.gui.other;

import com.example.ispw2.model.Evento;
import com.example.ispw2.model.User;
import com.example.ispw2.view.gui.controller.SchedaEventoGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.io.IOException;

public class EventoCellOrganizzatore extends ListCell<Evento> {


    private final HBox container = new HBox();
    private final Label nomeEvento = new Label();
    private final Button dettagliButton = new Button("Dettagli");

    protected User user;


    public void setUser(User user) {
        this.user = user;
    }

    public EventoCellOrganizzatore() {
        container.setSpacing(15);  // Spaziatura fra label e bottone
        container.getChildren().addAll(nomeEvento, dettagliButton);

        /*
         Non implementato da qui in poi
         */

        /* dettagliButton.setOnAction(event -> {
            Evento selectedEvento = getItem();
            if (selectedEvento != null) {
                apriSchedaEvento(selectedEvento);
            }
        });*/
    }

    @Override
    protected void updateItem(Evento evento, boolean empty) {
        super.updateItem(evento, empty);

        if (empty || evento == null) {
            setText(null);
            setGraphic(null);
        } else {
            nomeEvento.setText(evento.getNome());
            setGraphic(container);
        }
    }
}
