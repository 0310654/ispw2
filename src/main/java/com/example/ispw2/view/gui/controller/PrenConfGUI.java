package com.example.ispw2.view.gui.controller;

import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.model.User;
import com.example.ispw2.altro.configurations.Configurations;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class PrenConfGUI {
    @FXML
    private TableView<Prenotazione> tableInfo;

    @FXML
    private TableColumn<Prenotazione, String> col_nome_evento;
    @FXML
    private TableColumn<Prenotazione, String> col_cod_ticket;
    @FXML
    private TableColumn<Prenotazione, String> col_nome_cliente;
    @FXML
    private TableColumn<Prenotazione, String> col_cognome;
    @FXML
    private TableColumn<Prenotazione, String> col_data_evento;
    @FXML
    private TableColumn<Prenotazione, String> col_data_pren;
    @FXML
    private TableColumn<Prenotazione, String> col_stato_pren;

    @FXML
    private javafx.scene.control.Button backButton;

    protected User user;
    public void setUser(User user) {
        this.user = user;
    }

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);


    @FXML
    public void initialize() {
        col_nome_evento.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getNome_evento()));

        col_cod_ticket.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCod_prenotazione()));

        col_nome_cliente.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getNome()));

        col_cognome.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCognome()));

        col_stato_pren.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getStato_prenotazione()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        col_data_evento.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getData_evento().format(formatter)));

        col_data_pren.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getData_prenotazione().format(formatter)));

        Prenotazione prenotazione = PrenotazioniController.getInstance().getPrenotazione();

        if (prenotazione != null) {
            tableInfo.getItems().setAll(List.of(prenotazione));
        }
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
