package com.example.ispw2.view.cli.InitialStates;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InitialState extends State {

    @Override
    public void execute(StateMachine stateMachine) {
        Scanner input = new Scanner(System.in);
        int choice;

        showMenu();

        while (true) {
            try {
                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1 -> stateMachine.goNext(new LoginState());
                    case 2 -> stateMachine.goNext(new RegisterState());
                    default -> Printer.invalidChoicePrint();
                }

            } catch (InputMismatchException e) {
                input.nextLine();
                Printer.invalidChoicePrint();
            } catch (NoSuchElementException e) {
                Printer.invalidChoicePrint();
            }
        }
    }


    @Override
    public void showMenu() {
        Printer.println("Scegli una delle seguenti opzioni: ");
        Printer.println("1) Login");
        Printer.println("2) Registrazione");
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------BENVENUTO TO MYEVENT--------------");
        Printer.println("Per continuare, è necessario un account");
    }

}