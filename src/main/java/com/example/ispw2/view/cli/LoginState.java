package com.example.ispw2.view.cli;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.User;
import com.example.ispw2.view.cli.Cliente.HomeClienteState;
import com.example.ispw2.view.cli.Organizzatore.HomeOrganizzatoreState;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoginState extends State{

    @Override
    public void execute(StateMachine stateMachine) {
        LoginBean loginBean = this.authenticate();
        LoginController loginController = LoginController.getInstance();
        User user = null;

        try {
            user = loginController.start(loginBean);
        } catch (UserNonTrovatoException e) {
            Printer.errorPrint("Credenziali errate. Riprova...");
            this.execute(stateMachine);
        } catch (CredenzialiErrateException e) {
            Printer.errorPrint("Credenziali errate. Riprova...");
            this.execute(stateMachine);
        } catch (DAOException e) {
            Printer.errorPrint("Error occurred during login. Try again...");
            this.execute(stateMachine);
        }

        State homeState = null;

        if (user instanceof Organizzatore organizzatore) {
            homeState = new HomeOrganizzatoreState(organizzatore);
        } else if (user instanceof Cliente cliente) {
            homeState = new HomeClienteState(cliente);
        }

        stateMachine.goNext(homeState);
    }


    private LoginBean authenticate() {
        var in = new Scanner(System.in);
        String password;
        String email;

        while (true) {
            try {
                Printer.print("email: ");
                email = in.nextLine();
                break;
            } catch (NoSuchElementException e) {
                Printer.println("Inserire un'email!");
            }
        }

        while (true) {
            try {
                Printer.print("password: ");
                password = in.nextLine();
                break;
            } catch (NoSuchElementException e) {
                Printer.println("Inserire una password!");
            }
        }

        return new LoginBean(email, password);

    }
}
