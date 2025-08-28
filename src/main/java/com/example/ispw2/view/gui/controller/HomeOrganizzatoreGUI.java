package com.example.ispw2.view.gui.controller;

import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.bean.FiltriBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.HomeOrganizzatoreController;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.view.gui.other.Configurations;
import com.example.ispw2.view.gui.other.EventoCell;
import com.example.ispw2.view.gui.other.EventoCellOrganizzatore;
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

public class HomeOrganizzatoreGUI {
    @FXML
    public Button logoutBtn;
    @FXML
    public ListView listaEventi;
    @FXML
    public Button aggiungiEventoBtn;

    protected Organizzatore organizzatore;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);


    public HomeOrganizzatoreGUI(Organizzatore organizzatore) {
        this.organizzatore = organizzatore;
    }



    public void initialize(){
        ArrayList<Evento> lista = HomeOrganizzatoreController.getInstance().getEventi();
        // Popolo la ListView
        listaEventi.getItems().addAll(lista);

        // Imposto la cell factory personalizzata
        listaEventi.setCellFactory(listView -> new EventoCellOrganizzatore());
    }


    @FXML
    public void aggiungiEvento(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HomeOrganizzatoreGUI.class.getResource("/com/example/ispw2/aggiungiEvento-view.fxml"));
            loader.setControllerFactory(c -> new AggiungiEventoGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) aggiungiEventoBtn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in HomeOrganizzatoreGUI " + e.getMessage());
        }
    }

    @FXML
    public void logout(){
        try {
            FXMLLoader loader = new FXMLLoader(HomeClienteGUI.class.getResource("/com/example/ispw2/login-view.fxml"));
            loader.setControllerFactory(c -> new LoginViewGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in HomeOrganizzatoreGUI " + e.getMessage());
        }
    }
}
