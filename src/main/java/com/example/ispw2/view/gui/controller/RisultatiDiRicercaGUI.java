package com.example.ispw2.view.gui.controller;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.User;
import com.example.ispw2.view.gui.other.*;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.model.Evento;
import com.example.ispw2.altro.configurations.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RisultatiDiRicercaGUI {

    @FXML
    public Button backButton;

    @FXML
    public ListView listaEventi;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    protected User user;
    public void setUser(User user) {
        this.user = user;
    }


    @FXML
    public void backButton(ActionEvent actionEvent) {
        try {
            // Carica la HomeCliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ispw2/ricerca-view.fxml"));
            loader.setControllerFactory(c -> new HomeClienteGUI((Cliente) user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            logger.severe("Error in RisultatiDiRicercaGUI " + e.getMessage());
        }
    }

    public void initialize(){
        ArrayList<Evento> eventiRisultanti = HomeClienteController.getInstance().getEventiFiltrati();
        // Popolo la ListView
        listaEventi.getItems().addAll(eventiRisultanti);
        // Imposto la cell factory personalizzata
        listaEventi.setCellFactory(listView -> new EventoCell());
    }

}
