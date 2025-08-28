package com.example.ispw2.view.gui.controller;

import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.User;
import com.example.ispw2.view.gui.other.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class EventoSalvatoGUI {

    public Button tornaAllaHomeBTN;
    protected User user;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void tornaAllaHome(ActionEvent actionEvent) {
        try {
            // Carica la HomeCliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ispw2/homeorganizzatore-view.fxml"));

            // Passiamo l'utente loggato al controller della Home
            loader.setControllerFactory(c -> new HomeOrganizzatoreGUI((Organizzatore) user));

            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            // Recupera lo stage attuale e cambia scena
            Stage stage = (Stage) tornaAllaHomeBTN.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            logger.severe("Error in EventoSalvatoGUI " + e.getMessage());
            e.printStackTrace();
        }
    }
}
