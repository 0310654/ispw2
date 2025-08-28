package com.example.ispw2.view.gui.controller;

import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.model.User;
import com.example.ispw2.view.gui.other.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class PrenConfGUI {
    public Button backButton;
    public TableColumn col_nome_evento;
    public TableColumn col_cod_ticket;
    public TableColumn col_nome_cliente;
    public TableColumn col_cognome;
    public TableColumn col_data_evento;
    public TableColumn col_data_pren;
    public TableColumn col_stato_pren;
    public TableView<Prenotazione> tableInfo;

    protected User user;
    public void setUser(User user) {
        this.user = user;
    }

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);


    @FXML
    public void initialize() {
        Prenotazione prenotazione = PrenotazioniController.getInstance().getPrenotazioni();
        // Popola la TableView con i dati del DAO
        tableInfo.getItems().setAll(prenotazione);
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
            logger.severe("Error in PrenConfGUI " + e.getMessage());
            e.printStackTrace();
        }
    }

}
