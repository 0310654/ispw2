package com.example.ispw2.view.cli.InitialStates;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.bean.RegisterBean;
import com.example.ispw2.controller.RegisterController;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.EmailGiaInUsoException;
import com.example.ispw2.engineering.exceptions.InvalidEmailException;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.util.Scanner;
import java.util.regex.Pattern;


public class RegisterState extends State {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(StateMachine stateMachine) {
        Printer.print("Nome: ");
        String name = scanner.next();

        Printer.print("Cognome: ");
        String surname = scanner.next();

        String email;
        while (true) {
            try{
                Printer.print("Email: ");
                email = scanner.nextLine();
                isValidEmail(email);
                break;
            } catch(InvalidEmailException e){
                Printer.errorPrint("Email non valida! Riprova...");
            }
        }

        String password;
        while(true) {
            Printer.print("Password: ");
            password = scanner.next();

            Printer.print("Conferma Password: ");
            String confirmPassword = scanner.next();

            if(password.equals(confirmPassword)) break;
            else Printer.println("Le password devono essere uguali.");
        }

        try {
            RegisterController registerController = RegisterController.getInstance();
            RegisterBean registerBean = new RegisterBean(name, surname, email, password);
            registerController.registraCliente(registerBean);
        } catch (EmailGiaInUsoException e) {
            Printer.errorPrint("Email già in uso.");
            stateMachine.goBack();
        } catch (DAOException e) {
            Printer.errorPrint("Error durante la registrazione. Riprova...");
            this.execute(stateMachine);
        }

        Printer.println("Registrato con successo!");
        stateMachine.goBack();
    }

    private static void isValidEmail(String email) throws InvalidEmailException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------REGISTRATI--------------");
    }
}
