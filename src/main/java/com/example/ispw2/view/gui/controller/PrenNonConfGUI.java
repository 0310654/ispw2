package com.example.ispw2.view.gui.controller;

import com.example.ispw2.model.Cliente;
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

public class PrenNonConfGUI {
    public Button backButton;
    protected User user;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);


    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        try {
            // Carica la HomeCliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ispw2/homecliente-view.fxml"));

            // Passiamo l'utente loggato al controller della Home
            loader.setControllerFactory(c -> new HomeClienteGUI((Cliente) user));

            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            // Recupera lo stage attuale e cambia scena
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            logger.severe("Error in PrenNonConfGUI " + e.getMessage());
            e.printStackTrace();
        }
    }
}
