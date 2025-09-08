package com.example.ispw2.view.gui.controller;

import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.view.gui.other.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SchedaEventoGUI {

    private Cliente user;
    @FXML
    public Button backButton;
    @FXML
    public TextField nomeEventoLabel;
    @FXML
    public TextField infoEventoLabel;
    @FXML
    public SplitMenuButton choiceSettore;

    @FXML
    public SplitMenuButton iTuoiDati;
    @FXML
    public Button prenotaBtn;
    public MenuItem nomeField;
    public MenuItem cognomeField;

    private Evento evento;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public SchedaEventoGUI() {
        this.user = (Cliente) LoginController.getInstance().getUser();
    }

    @FXML
    public void setEvento(Evento evento) {
        this.evento = evento;
        nomeEventoLabel.setText(evento.getNome());
        infoEventoLabel.setText(evento.getDescrizione());
        nomeField.setText("Inserisci nome");
        cognomeField.setText("Inserisci cognome");

        ArrayList<String> settori = evento.getSettore();
        for (String sett : settori) {
            MenuItem item = new MenuItem(sett);

            item.setOnAction(event -> {
                // Aggiorno il testo del pulsante
                choiceSettore.setText(sett);

            });
            choiceSettore.getItems().add(item);
        }

        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        iTuoiDati.setText(nome + " " + cognome);
    }


    @FXML
    public void prenota(ActionEvent actionEvent) {

        //quando clicco prenota, mi va sulla schermata
        // o di prenotazione confermata se non ho prenotazioni pendenti
        //oppure sulla schermata di prenotazioni non confermate

        boolean pendenti;
        if(user.getPrenotazioni_pendenti()==null){
            pendenti = false;
        }else{
            pendenti = true;
        }

        String cod_prenotazione = PrenotazioniController.getInstance().newCodice();
        PrenotazioniBean prenotazioneBean = new PrenotazioniBean(evento.getNome(),
                cod_prenotazione,
                user.getName(),
                user.getSurname(),
                evento.getData_evento(),
                LocalDateTime.now() ,
                "PENDENTE");

        PrenotazioniController.getInstance();

        try {
            PrenotazioniController.getInstance().prenotaEvento(prenotazioneBean);

        }catch (MaxPendingBorrowsException e) {
            try {
                FXMLLoader loader = new FXMLLoader(SchedaEventoGUI.class.getResource("/com/example/ispw2/prenotazioneNonConf-view.fxml"));
                loader.setControllerFactory(c -> new PrenNonConfGUI());
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) prenotaBtn.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException er) {
                logger.severe("Error in SchedaEventoGUI " + er.getMessage());
            }
        }

        loadConfirmation();

    }

    public void loadConfirmation() {

        try {
            FXMLLoader loader = new FXMLLoader(SchedaEventoGUI.class.getResource("/com/example/ispw2/prenotazioneConfermata-view.fxml"));
            loader.setControllerFactory(c -> new PrenConfGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in SchedaEventoGUI " + e.getMessage());
        }
    }


    @FXML
    private void back() {
        try {
            FXMLLoader loader = new FXMLLoader(SchedaEventoGUI.class.getResource("/com/example/ispw2/ricerca-view.fxml"));
            loader.setControllerFactory(c -> new LoginViewGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in SchedaEventoGUI " + e.getMessage());
        }
    }
}
