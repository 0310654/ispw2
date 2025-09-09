package com.example.ispw2.view.gui.controller;

import com.example.ispw2.engineering.bean.LoginBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.engineering.exceptions.CredenzialiErrateException;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.User;
import com.example.ispw2.altro.configurations.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class LoginViewGUI {


    @FXML
    public TextField emailField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button loginButton;

    @FXML
    public Hyperlink registerLink;

    @FXML
    public Label wrongCredentials;
    @FXML
    public Label credentialsError;
    @FXML
    public Button autopopolaCampi;

    protected User user;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    private static final String WRONG_CREDENTIALS = "Credenziali errate.";

    @FXML
    //tasto di login
    public void handleLogin(ActionEvent actionEvent) {

        String email;
        String password;

        //controlla se i campi sono compilati
        if (!this.emailField.getText().isEmpty() && !this.passwordField.getText().isEmpty()) {
            email = this.emailField.getText();
            password = this.passwordField.getText();
        } else {
            credentialsError.setText("Completa tutti i campi!");
            credentialsError.setVisible(true);
            return;
        }

        try {
            System.out.println("email inserita: " + email);

            LoginBean cred = new LoginBean(email, password);

            //istanziamo il controller applicativo che si deve occupare del login e gli passiamo il bean contenente le credenziali
            LoginController loginController = LoginController.getInstance();

            //prendiamo i dati dell'utente loggato (sessione)
            this.user = loginController.start(cred);
            System.out.println("user type: " + this.user.getType());

            credentialsError.setVisible(false);
            wrongCredentials.setVisible(false);

            //in base al ruolo dell'utente loggato carichiamo la pagina corretta della home
            loadHome(user);

        }  catch (UserNonTrovatoException | CredenzialiErrateException e) {
            wrongCredentials.setText(WRONG_CREDENTIALS);
            wrongCredentials.setVisible(true);
            credentialsError.setVisible(false);

        } catch (DAOException e) {
            wrongCredentials.setText("Errore durante il login. Riprovare...");
            wrongCredentials.setVisible(true);
            credentialsError.setVisible(false);

        }

    }

    @FXML
    public void handleRegisterRedirect(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginViewGUI.class.getResource("/com/example/ispw2/registrazione-view.fxml"));
            loader.setControllerFactory(c -> new RegisterViewGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) credentialsError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in LoginViewGUI " + e.getMessage());
        }
    }


    @FXML
    //metodo che carica la home corretta in base al ruolo
    public void loadHome(User user) {

        try {
            FXMLLoader loader;
            System.out.println(user.getType());

            if (user.getType().equals("organizzatore")) {
                loader = new FXMLLoader(LoginViewGUI.class.getResource("/com/example/ispw2/homeorganizzatore-view.fxml"));
                loader.setControllerFactory(c -> new HomeOrganizzatoreGUI((Organizzatore) user));
            } else {
                loader = new FXMLLoader(LoginViewGUI.class.getResource("/com/example/ispw2/homecliente-view.fxml"));
                loader.setControllerFactory(c -> new HomeClienteGUI((Cliente) user));
            }
            Parent parent = loader.load();
            Stage stage = (Stage) credentialsError.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(parent);
            //stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in LoginViewGUI " + e.getMessage());
        }
    }

    public void handleAutopopolaCampi() {
        this.emailField.setText("cliente@email.com");
        this.passwordField.setText("password");
    }
}
