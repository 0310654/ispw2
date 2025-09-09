package com.example.ispw2.view.gui.controller;

import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class NoResultsViewGUI {
    @FXML
    public ImageView backBtn;
    @FXML
    public Button tornaAllaHomeBTN;

    protected User user;


    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void tornaAllaHome(ActionEvent actionEvent) {
        try {
            // Carica la HomeCliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ispw2/homecliente-view.fxml"));

            // Passiamo l'utente loggato al controller della Home
            loader.setControllerFactory(c -> new HomeClienteGUI((Cliente) user));

            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            // Recupera lo stage attuale e cambia scena
            Stage stage = (Stage) tornaAllaHomeBTN.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Errore durante il caricamento della Home");
            e.printStackTrace();
        }
    }
}
