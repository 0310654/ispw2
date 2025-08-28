package com.example.ispw2.view.gui.controller;

import com.example.ispw2.bean.FiltriBean;
import com.example.ispw2.bean.RegisterBean;
import com.example.ispw2.bean.SelectedBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.RegisterController;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.view.gui.other.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HomeClienteGUI {
    @FXML
    public MenuButton eventifltBtn;
    @FXML
    public MenuButton localitaFltBtn;
    @FXML
    public DatePicker calendarioFlt;
    @FXML
    public MenuButton prezzoFltBtn;
    @FXML
    public Button cercaBtn;


    protected Cliente cliente;

    @FXML
    private ListView<String> listaEventiFiltrati;


    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public HomeClienteGUI(Cliente cliente) {
        this.cliente = cliente;
    }

    @FXML
    public void initialize() {
        FiltriBean filtriBean = HomeClienteController.getInstance().getFiltri();
        eventifltBtn.getItems().clear();
        localitaFltBtn.getItems().clear();
        prezzoFltBtn.getItems().clear();

        for(String s: filtriBean.getEventi()){
            MenuItem item = new MenuItem(s);
            item.setOnAction(e -> handleSelection(s, eventifltBtn));
            eventifltBtn.getItems().add(item);

        }
        for(String s: filtriBean.getLocalita()){
            MenuItem item = new MenuItem(s);
            item.setOnAction(e -> handleSelection(s, localitaFltBtn));
            localitaFltBtn.getItems().add(item);
        }
        for(String s: filtriBean.getPrezzo()){
            MenuItem item = new MenuItem(s);
            item.setOnAction(e -> handleSelection(s, prezzoFltBtn));
            prezzoFltBtn.getItems().add(item);
        }
    }

    @FXML
    public void cercaBtnClick() {

        String tipoSelezionato = eventifltBtn.getText();
        String localitaSelezionata = localitaFltBtn.getText();
        LocalDateTime dataSelezionata = calendarioFlt.getValue() != null ?
                calendarioFlt.getValue().atStartOfDay() : null;
        String prezzoSelezionato = prezzoFltBtn.getText();

        SelectedBean selectedBean = new SelectedBean(tipoSelezionato, localitaSelezionata, dataSelezionata, prezzoSelezionato);

        //passo al controller applicativo il bean contenente i filtri per cercare l'evento
        ArrayList<Evento> results = HomeClienteController.getInstance().filtriSelezionati(selectedBean);
        System.out.println(results.size());
        for (Evento evento : results) {
            System.out.println(evento.getNome());
            System.out.println(evento.toString());
        }
        if(results.isEmpty()){
            try {
                FXMLLoader loader = new FXMLLoader(HomeClienteGUI.class.getResource("/com/example/ispw2/NessunRisultatoTrovato-view.fxml"));
                loader.setControllerFactory(c -> new NoResultsViewGUI());
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) cercaBtn.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("Error in HomeClienteGUI " + e.getMessage());
            }
        }else{
            try {
                FXMLLoader loader = new FXMLLoader(HomeClienteGUI.class.getResource("/com/example/ispw2/ricerca-view.fxml"));
                loader.setControllerFactory(c -> new RisultatiDiRicercaGUI());
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) cercaBtn.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("Error in HomeClienteGUI " + e.getMessage());
            }
        }

    }

    private void handleSelection(String value, MenuButton menuButton) {
        menuButton.setText(value);
    }

    public void logout(){
        try {

            /*
            Prima mantenevamo i dati relativi alla sessione passando un bean di controller in controller.
            eliminare la sessione significa tornare alla pagina di login senza passare alcun parametro contente i dati della sessione
            quindi di base il controller grafico deve al massimo portare ad una pagina di conferma del logout (qui non è fatto), poi semplicemente carica il login
            */

            FXMLLoader loader = new FXMLLoader(HomeClienteGUI.class.getResource("/com/example/ispw2/login-view.fxml"));
            loader.setControllerFactory(c -> new LoginViewGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) cercaBtn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in HomeClienteGUI " + e.getMessage());
        }
    }
}
