package com.example.ispw2.view.gui.other;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.model.*;
import com.example.ispw2.view.gui.controller.HomeClienteGUI;
import com.example.ispw2.view.gui.controller.NoResultsViewGUI;
import com.example.ispw2.view.gui.controller.SchedaEventoGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class EventoCell extends ListCell<Evento> {
    private final HBox container = new HBox();
    private final Label nomeEvento = new Label();
    private final Button dettagliButton = new Button("Dettagli");

    protected User user;


    public void setUser(User user) {
        this.user = user;
    }

    public EventoCell() {
        container.setSpacing(15);  // Spaziatura fra label e bottone
        container.getChildren().addAll(nomeEvento, dettagliButton);

        // Azione del pulsante
        dettagliButton.setOnAction(event -> {
            Evento selectedEvento = getItem();
            if (selectedEvento != null) {
                System.out.println(selectedEvento.getNome());
                apriSchedaEvento(selectedEvento);
            }
        });
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

    private void apriSchedaEvento(Evento evento) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ispw2/evento-view.fxml"));
            Parent parent = loader.load();
            // Passo i dati al controller della scheda evento
            SchedaEventoGUI schedaEventoGUI = loader.getController();
            schedaEventoGUI.setEvento(evento);

            loader.setControllerFactory(c -> new SchedaEventoGUI());

            Scene scene = new Scene(parent);

            Stage stage = (Stage) dettagliButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
