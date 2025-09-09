package com.example.ispw2.view.cli.InitialStates;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.bean.LoginBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.engineering.exceptions.CredenzialiErrateException;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.User;
import com.example.ispw2.view.cli.Cliente.HomeClienteState;
import com.example.ispw2.view.cli.Organizzatore.HomeOrganizzatoreState;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoginState extends State {

    @Override
    public void execute(StateMachine stateMachine) {
        LoginController loginController = LoginController.getInstance();
        User user = null;

        Scanner in = new Scanner(System.in); // Scanner condiviso per email e password

        while (user == null) {
            LoginBean loginBean = authenticate(in);
            if (loginBean == null) {
                Printer.println("Input terminato. Interruzione login.");
                return; // termina execute senza loop infinito
            }

            try {
                user = loginController.start(loginBean);
            } catch (UserNonTrovatoException | CredenzialiErrateException e) {
                Printer.errorPrint("Credenziali errate. Riprova...");
            } catch (DAOException e) {
                Printer.errorPrint("Errore durante il login. Riprova...");
            }
        }

        // Passaggio allo stato successivo
        State homeState;
        if (user instanceof Organizzatore organizzatore) {
            homeState = new HomeOrganizzatoreState(organizzatore);
        } else if (user instanceof Cliente cliente) {
            homeState = new HomeClienteState(cliente);
        } else {
            Printer.errorPrint("Tipo utente non riconosciuto.");
            return;
        }

        stateMachine.goNext(homeState);
    }

    private LoginBean authenticate(Scanner in) {
        String email = null;
        String password = null;

        try {
            Printer.print("email: ");
            if (in.hasNextLine()) {
                email = in.nextLine().trim();
            } else {
                return null; // EOF, termina input
            }

            Printer.print("password: ");
            if (in.hasNextLine()) {
                password = in.nextLine().trim();
            } else {
                return null; // EOF, termina input
            }

        } catch (NoSuchElementException e) {
            return null; // EOF o stream chiuso
        }

        if (email.isEmpty() || password.isEmpty()) {
            Printer.errorPrint("Email o password vuota. Riprova...");
            return authenticate(in); // riprova input
        }

        return new LoginBean(email, password);
    }


    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------LOGIN--------------");
    }
}
