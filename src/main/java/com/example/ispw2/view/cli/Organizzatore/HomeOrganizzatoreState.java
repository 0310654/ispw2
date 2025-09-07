package com.example.ispw2.view.cli.Organizzatore;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.view.cli.Implementations;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HomeOrganizzatoreState extends State {

    private Organizzatore organizzatore;

    public HomeOrganizzatoreState(Organizzatore organizzatore) {
        super();
        this.organizzatore = organizzatore;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        showMenu();

        Scanner scan = new Scanner(System.in);
        int choice;

        while(true) {
            Printer.print("Enter your choice: ");

            try{
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice){
                    case 1 -> stateMachine.goNext(new ImieiEventiState(organizzatore));
                    case 2 -> stateMachine.goNext(new AddEventoState(organizzatore));
                    case 3 -> new Implementations().start();
                    default -> Printer.invalidChoicePrint();
                }

            } catch (InputMismatchException e){
                Printer.invalidChoicePrint();
                scan.nextLine();
            }catch (NoSuchElementException e){
                Printer.invalidChoicePrint();
            }
        }
    }

    @Override
    public void showMenu() {
        Printer.println("Scegli una delle seguenti opzioni: ");
        Printer.println("1) mostra i miei eventi");
        Printer.println("2) aggiungi un evento");
        Printer.println("3) logout");
    }
}
