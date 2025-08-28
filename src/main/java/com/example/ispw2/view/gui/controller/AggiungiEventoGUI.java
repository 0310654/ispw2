package com.example.ispw2.view.gui.controller;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.EventBean;
import com.example.ispw2.bean.RegisterBean;
import com.example.ispw2.controller.AddEventoController;
import com.example.ispw2.controller.HomeOrganizzatoreController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.controller.RegisterController;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.exceptions.InvalidEmailException;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.User;
import com.example.ispw2.view.gui.other.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AggiungiEventoGUI {
    @FXML
    public TextField nomeField;
    @FXML
    public TextField enteFiled;
    @FXML
    public TextField tipoField;
    @FXML
    public TextField localitaField;
    @FXML
    public DatePicker dataField;
    @FXML
    public TextField descrizioneField;

    @FXML
    public VBox settoriContainer;
    @FXML
    public Button addSettoreButton;
    @FXML
    public Label errorLabel;
    @FXML
    public Button aggiungiEventoButton;
    @FXML
    public Button backBtn;

    private final List<HBox> settori = new ArrayList<>();

    protected User user;
    public void setUser(User user) {
        this.user = user;
    }

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);


    @FXML
    public void initialize() {
        // Aggiungiamo la prima riga di default
        aggiungiRigaSettore();
    }


    // Metodo per aggiungere una nuova riga di settore
    @FXML
    public void handleAddSettore() {
        aggiungiRigaSettore();
    }

    private void aggiungiRigaSettore() {
        HBox row = new HBox(10);
        row.setPrefHeight(40);

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome settore");
        nomeField.setPrefWidth(120);

        TextField postiField = new TextField();
        postiField.setPromptText("Posti disponibili");
        postiField.setPrefWidth(120);

        TextField prezzoField = new TextField();
        prezzoField.setPromptText("Prezzo (€)");
        prezzoField.setPrefWidth(100);

        // Aggiungiamo i campi alla riga
        row.getChildren().addAll(nomeField, postiField, prezzoField);

        // Aggiungiamo la riga al contenitore principale
        settoriContainer.getChildren().add(row);
    }



    @FXML
    public void aggiungiEvento(ActionEvent actionEvent) {
        String codice; //da generare
        String nome;
        String organizzatore; //da prendere dal mio account
        String ente;
        String tipo_evento;
        String localita;
        LocalDateTime data_evento;
        String descrizione;

        ArrayList<String> settore = new ArrayList<>();
        ArrayList<Integer> num_posti_settore = new ArrayList<>();
        ArrayList<Double> prezzo_settore = new ArrayList<>();
        ArrayList<Integer>  disponibilita_settore = new ArrayList<>();

        //prendo il mio nome
        organizzatore = LoginController.getInstance().getUser().getName();

        //funzione che crea un codice casuale per il mio evento
        codice = AddEventoController.getInstance().newCodiceEvento();

        if(!this.nomeField.getText().isEmpty() && this.enteFiled.getText().isEmpty() && this.tipoField.getText().isEmpty() && this.localitaField.getText().isEmpty() && this.dataField.getValue() != null && this.descrizioneField.getText().isEmpty()){

            nome = this.nomeField.getText();
            ente = this.enteFiled.getText();
            tipo_evento = this.tipoField.getText();
            localita = this.localitaField.getText();
            data_evento = dataField.getValue() != null ?
                    dataField.getValue().atStartOfDay() : null;
            descrizione = this.descrizioneField.getText();
        }else{
            errorLabel.setText("Compila tutti i campi!");
            return;
        }

        for (HBox hbox : settori) {
            List<Node> children = hbox.getChildren();

            TextField nomeField = (TextField) children.get(0);
            TextField postiField = (TextField) children.get(1);
            TextField prezzoField = (TextField) children.get(2);

            String nomeText = nomeField.getText().trim();
            String postiText = postiField.getText().trim();
            String prezzoText = prezzoField.getText().trim();

            // Controllo campi vuoti
            if (nomeText.isEmpty() || postiText.isEmpty() || prezzoText.isEmpty()) {
                System.out.println("Attenzione: alcuni campi sono vuoti!");
                continue;
            }

            try {
                double posti = Double.parseDouble(postiText);
                double prezzo = Double.parseDouble(prezzoText);

                settore.add(nomeText);
                num_posti_settore.add((int) posti);
                prezzo_settore.add(prezzo);
                //inizialmente i posti sono tutti disponibili
                disponibilita_settore.add((int) posti);

            } catch (NumberFormatException e) {
                System.out.println("Errore: posti o prezzo non numerici per settore " + nome);
            }
        }

        //inserisco gli input ottenuti in BEAN
        EventBean eventBean = new EventBean(codice, nome, organizzatore, ente, tipo_evento, localita, settore, disponibilita_settore, prezzo_settore,data_evento, num_posti_settore, descrizione);;

        //TODO controllare tutti i try/catch
        //istanzio un controller applicativo e gli passo il bean contenente i dati per registrare l'evento
        AddEventoController.getInstance().addEvento(eventBean);
        loadConfirmation();

    }

    private void loadConfirmation() {
        try {
            FXMLLoader loader = new FXMLLoader(AggiungiEventoGUI.class.getResource("/com/example/ispw2/eventoSalvato-view.fxml"));
            loader.setControllerFactory(c -> new EventoSalvatoGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in AggiungiEventoGUI " + e.getMessage());
        }
    }


    @FXML
    public void back(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ispw2/homeorganizzatore-view.fxml"));

            loader.setControllerFactory(c -> new HomeOrganizzatoreGUI((Organizzatore) user));

            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            // Recupera lo stage attuale e cambia scena
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            logger.severe("Error in AggiungiEventoGUI " + e.getMessage());
            e.printStackTrace();
        }
    }


}


