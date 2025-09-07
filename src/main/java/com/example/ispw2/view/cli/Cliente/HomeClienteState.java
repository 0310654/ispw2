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
        int choice;

        while(true) {
            Printer.print("Inserisci la tua scelta: ");

            try{
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1 -> Printer.println("Non ancora implementato.");
                    case 2 -> stateMachine.goNext(new SearchEventState(cliente));
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
        Printer.println("1) mostra il mio profilo");
        Printer.println("2) cerca un evento");
        Printer.println("3) logout");
    }
}
