package com.example.ispw2.view.cli.Cliente;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.view.cli.Implementations;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HomeClienteState extends State {
    private Cliente cliente;

    public HomeClienteState(Cliente cliente) {
        super();
        this.cliente = cliente;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        showMenu();
        Scanner scan = new Scanner(System.in);

        while (true) {
            Printer.print("Inserisci la tua scelta: ");

            String line;
            if (!scan.hasNextLine()) {
                Printer.println("Fine input, terminazione loop...");
                break; // EOF raggiunto, esci
            }

            line = scan.nextLine().trim();

            if (line.isEmpty()) {
                Printer.println("Nessuna scelta inserita, uscita...");
                break; // input vuoto, esci
            }

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                Printer.invalidChoicePrint();
                continue; // non numerico, richiedi di nuovo
            }

            if (choice == 1) {
                Printer.println("Non ancora implementato.");
            } else if (choice == 2) {
                stateMachine.goNext(new SearchEventState(cliente));
                return; // esci dal loop
            } else if (choice == 3) {
                new Implementations().start();
                return; // esci dal loop
            } else {
                Printer.invalidChoicePrint();
            }
        }
    }




    @Override
    public void showMenu() {
        Printer.println("Scegli una delle seguenti opzioni: ");
        Printer.println("1) mostra il mio profilo");
        Printer.println("2) cerca un evento");
        Printer.println("3) logout");
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------HOME CLIENTE--------------");
    }
}
