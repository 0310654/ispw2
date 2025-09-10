package com.example.ispw2.view.gui.controller;

import com.example.ispw2.engineering.bean.PrenotazioniBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.engineering.exceptions.MaxPendingResException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.altro.configurations.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SchedaEventoGUI {

    private Cliente user;
    @FXML
    public Button backButton;
    @FXML private Label nomeEventoLabel;
    @FXML private Label infoEventoLabel;
    @FXML
    public SplitMenuButton choiceSettore;
    @FXML
    public Button prenotaBtn;

    private Evento evento;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public SchedaEventoGUI() {
        this.user = (Cliente) LoginController.getInstance().getUser();
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
        aggiornaUI();
    }

    private void aggiornaUI() {
        if (evento == null) return;

        nomeEventoLabel.setText(evento.getNome());

        String infoevento = "Descrizione:\n\t" + evento.getDescrizione() + "\n" +
                "Località:\n\t" + evento.getLocalita() + "\n" +
                "Codice:\n\t" + evento.getCodice() + "\n";
        infoEventoLabel.setText(infoevento);
        infoEventoLabel.setWrapText(true);

        // Pulisci le voci precedenti
        choiceSettore.getItems().clear();

        // Popola il dropdown e imposta il default
        boolean primoSettore = true;
        for (String s : evento.getSettore()) {
            if (s != null && !s.isEmpty()) {
                MenuItem item = new MenuItem(s);
                item.setOnAction(e -> choiceSettore.setText(s));
                choiceSettore.getItems().add(item);

                // Imposta il primo settore come default
                if (primoSettore) {
                    choiceSettore.setText(s);
                    primoSettore = false;
                }
            }
        }
    }



    @FXML
    public void prenota(ActionEvent actionEvent) {

        //quando clicco prenota, mi va sulla schermata
        // o di prenotazione confermata se non ho prenotazioni pendenti
        //oppure sulla schermata di prenotazioni non confermate

        String cod_prenotazione = PrenotazioniController.getInstance().newCodice();
        PrenotazioniBean prenotazioneBean = new PrenotazioniBean(evento.getNome(),
                cod_prenotazione,
                user.getName(),
                user.getSurname(),
                evento.getData_evento(),
                LocalDateTime.now(),
                "PENDENTE",
                choiceSettore.getText());

        try {
            PrenotazioniController.getInstance().prenotaEvento(prenotazioneBean);

        }catch (MaxPendingResException e) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ispw2/ricerca-view.fxml"));

            // Passiamo l'utente loggato al controller della Home
            loader.setControllerFactory(c -> new RisultatiDiRicercaGUI());

            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            // Recupera lo stage attuale e cambia scena
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Errore durante il caricamento della schermata dei risultati di ricerca");
            e.printStackTrace();
        }
    }
}
