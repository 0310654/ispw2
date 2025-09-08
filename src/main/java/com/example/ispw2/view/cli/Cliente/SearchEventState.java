package com.example.ispw2.view.cli.Cliente;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.bean.SelectedBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;
import com.example.ispw2.view.gui.other.Configurations;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class SearchEventState extends State {
    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    private Cliente cliente;
    public SearchEventState(Cliente cliente) {
        super();
        this.cliente = cliente;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        var in = new Scanner(System.in);

        String tipoEvento = getTipoEvento(in);
        String localita = getLocalita(in);
        String prezzo = getPrezzo(in);
        LocalDateTime dataEvento = getDataEvento(in);

        SelectedBean selectedBean = new SelectedBean(tipoEvento,localita,dataEvento,prezzo);
        HomeClienteController homeClienteController = HomeClienteController.getInstance();
        ArrayList<Evento> eventiFiltrati = homeClienteController.filtriSelezionati(selectedBean);

        Evento evento = selectEvent(eventiFiltrati, in);
        String cod_prenotazione = PrenotazioniController.getInstance().newCodice();
        PrenotazioniBean prenotazioniBean = new PrenotazioniBean(evento.getNome(),
                cod_prenotazione,
                cliente.getName(),
                cliente.getSurname(),
                evento.getData_evento(),
                LocalDateTime.now(),
                "PENDING");

        try {
            PrenotazioniController prenotazioniController = PrenotazioniController.getInstance();
           prenotazioniController.prenotaEvento(prenotazioniBean);
        } catch (MaxPendingBorrowsException e) {
            Printer.errorPrint("You have reached the maximum number of pending borrows.");
        }
        Printer.println("Prenotazione effettuata!\n Puoi trovarla nella tua area personale.");

        stateMachine.goBack();
    }


    private Evento selectEvent(ArrayList<Evento> eventiFiltrati, Scanner in) {
        if(eventiFiltrati.isEmpty()) {
            Printer.println("Nessun evento corrispondente trovato.");
            return null;
        }else {
            Printer.printlnBlu("Seleziona l'evento che ti interessa (inserisci il numero corrispondente): ");

            printEventi(eventiFiltrati);

            int choice;
            while (true) {
                try {
                    choice = in.nextInt();
                    in.nextLine();
                    break;

                } catch (InputMismatchException e) {
                    Printer.invalidChoicePrint();
                    in.nextLine();
                } catch (NoSuchElementException e) {
                    Printer.invalidChoicePrint();
                }
            }

            return eventiFiltrati.get(--choice);
        }

    }

    private void printEventi(List<Evento> eventiFiltrati) {

        int i = 1;

        for (Evento evento : eventiFiltrati) {
            Printer.print(i++ + ") " + evento.toString());
        }
    }

        //con Altro si intende: nessuna selezione
    private String getPrezzo(Scanner in) {
        Printer.println("Seleziona la fascia di prezzo:");
        Printer.println("1)50€-100€ ");
        Printer.println("2) 100€-200€");
        Printer.println("3) Altro");

        int scelta;
        do {
            Printer.print("Scelta: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 3);

        return switch (scelta) {
            case 1 -> "50€-100€";
            case 2 -> "100€-200€";
            default -> "Altro";
        };
    }

    private String getLocalita(Scanner in) {
        Printer.println("Seleziona il luogo:");
        Printer.println("1) Roma");
        Printer.println("2) Milano");
        Printer.println("3) Venezia");
        Printer.println("4) Altro");

        int scelta;
        do {
            Printer.print("Scelta: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 4);

        return switch (scelta) {
            case 1 -> "Roma";
            case 2 -> "Milano";
            case 3 -> "Venezia";
            default -> "Altro";
        };
    }

    private String getTipoEvento(Scanner in) {
        Printer.println("Seleziona il tipo di evento:");
        Printer.println("1) Concerto");
        Printer.println("2) Teatro");
        Printer.println("3) Cinema");
        Printer.println("4) Altro");

        int scelta;
        do {
            Printer.print("Scelta: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 4);

        return switch (scelta) {
            case 1 -> "Concerto";
            case 2 -> "Teatro";
            case 3 -> "Cinema";
            default -> "Altro";
        };
    }

    private LocalDateTime getDataEvento(Scanner in) {
        Printer.print("Inserisci la data dell'evento (YYYY-MM-DD): ");
        String input = in.next();
        return LocalDateTime.parse(input);
    }

}
