package com.example.ispw2.view.gui.controller;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.RegisterBean;
import com.example.ispw2.controller.RegisterController;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.exceptions.InvalidEmailException;
import com.example.ispw2.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterViewGUI {
    @FXML
    public Button backToLoginButton;
    public Button autopopolaCampi;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorLabel;

    protected User user;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @FXML
    void handleRegister(ActionEvent actionEvent) {

        //metodo attivato dal pulsante di conferma sulla schermata di registrazione

        String name;
        String surname;
        String email;
        String password;
        String confirmPassword;


        //se sono stati compilati tutti i campi
        if(!this.nomeField.getText().isEmpty() && !this.cognomeField.getText().isEmpty() && !this.emailField.getText().isEmpty() && !this.passwordField.getText().isEmpty() && !this.confirmPasswordField.getText().isEmpty()){

            //prendo i dati inseriti dall'utente
            name = this.nomeField.getText();
            surname = this.cognomeField.getText();


            //controllo la forma dell'email inserita
            email = this.emailField.getText();
            try{
                isValidEmail(email);
            } catch(InvalidEmailException e){
                errorLabel.setText("Email non valida.");
                return;
            }


            password = this.passwordField.getText();
            confirmPassword = this.confirmPasswordField.getText();

            if (!confirmPassword.equals(password)){
                errorLabel.setText("Inserire la stessa password!");
                return;
            }

        }
        else{
            errorLabel.setText("Compila tutti i campi!");
            return;
        }

        try {
            //inserisco gli input ottenuti in BEAN
            RegisterBean registerBean = new RegisterBean(name, surname, email, password);;

            //istanzio un controller applicativo e gli passo il bean contenente i dati per registrare l'utente
            RegisterController registerController = new RegisterController();
            registerController.registraCliente(registerBean);

            loadConfirmation();

        }catch(EmailGiaInUsoException e) {
            Printer.errorPrint("Application controller: " + e.getMessage());
            errorLabel.setText("Email già in uso.");
        } catch (DAOException e) {
            Printer.errorPrint("Application controller: " + e.getMessage());
            errorLabel.setText("Errore durante la registrazione. Riprova...");
        }
    }


    //funzione per il controllo della forma di email-------------------------------------------------
    public static void isValidEmail(String email) throws InvalidEmailException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    //cambio pagina: quando effettuato correttamente la registrazione--------------------------------------------------------------
    public void loadConfirmation () {
        try {
            FXMLLoader loader = new FXMLLoader(RegisterViewGUI.class.getResource("/com/example/ispw2/regConfermata-view.fxml"));
            loader.setControllerFactory(c -> new RegisterViewGUI());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            //logger.severe("Error in RegisterGUIController " + e.getMessage());
        }
    }


    public void handleBackToLogin () {
        try {
            FXMLLoader loader = new FXMLLoader(RegisterViewGUI.class.getResource("/com/example/ispw2/login-view.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            Stage stage = (Stage) backToLoginButton.getScene().getWindow();
            if (stage != null) {
                stage.setScene(scene);
                stage.show();
            } else {
                Printer.errorPrint("Schermata di login non trovata.");
            }

            assert stage != null;
            stage.setScene(scene);
        } catch (IOException e) {
            //logger.severe("Error in RegisterGUIController " + e.getMessage());
        }
    }

    public void handleAutopopolaCampiReg(ActionEvent actionEvent) {
        this.nomeField.setText("a");
        this.cognomeField.setText("b");
        this.emailField.setText("ab@email.com");
        this.passwordField.setText("pass");
        this.confirmPasswordField.setText("pass");
    }
}
