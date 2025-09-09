package com.example.ispw2.view.cli.Cliente;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.bean.PrenotazioniBean;
import com.example.ispw2.engineering.bean.SelectedBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.engineering.exceptions.MaxPendingResException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;
import com.example.ispw2.altro.configurations.Configurations;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
        if (evento == null) {
            stateMachine.goBack();
        }
        String settore = selectSettore(evento, in);
        String cod_prenotazione = PrenotazioniController.getInstance().newCodice();
        PrenotazioniBean prenotazioniBean = new PrenotazioniBean(evento.getNome(),
                cod_prenotazione,
                cliente.getName(),
                cliente.getSurname(),
                evento.getData_evento(),
                LocalDateTime.now(),
                "PENDING",
                settore);

        try {
            PrenotazioniController prenotazioniController = PrenotazioniController.getInstance();
           prenotazioniController.prenotaEvento(prenotazioniBean);
        } catch (MaxPendingResException e) {
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

    private String selectSettore(Evento evento, Scanner in) {
        Printer.printlnBlu("Seleziona il settore che ti interessa (inserisci il numero corrispondente): ");
        printSettori(evento.getSettore());
        int choice2;
        while (true) {
            try {
                choice2 = in.nextInt();
                in.nextLine();
                break;

            } catch (InputMismatchException e) {
                Printer.invalidChoicePrint();
                in.nextLine();
            } catch (NoSuchElementException e) {
                Printer.invalidChoicePrint();
            }
        }
        return evento.getSettore().get(--choice2);
    }

    private void printEventi(List<Evento> eventiFiltrati) {

        int i = 1;

        for (Evento evento : eventiFiltrati) {
            Printer.print(i++ + ") " + evento.toString());
            Printer.println("");
        }

    }

    private void printSettori(List<String> settori) {

        int i = 1;

        for (String s : settori) {
            Printer.print(i++ + ") " + s);
            Printer.println("");
        }

    }

        //con Altro si intende: nessuna selezione
    private String getPrezzo(Scanner in) {
        Printer.println("Seleziona la fascia di prezzo:");
        Printer.println("1)50€-100€ ");
        Printer.println("2) 100€-200€");
        Printer.println("3) Nessun filtro");

        int scelta;
        do {
            Printer.print("Scelta: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 3);

        return switch (scelta) {
            case 1 -> "50€-100€";
            case 2 -> "100€-200€";
            default -> "null";
        };
    }

    private String getLocalita(Scanner in) {
        Printer.println("Seleziona il luogo:");
        Printer.println("1) Roma");
        Printer.println("2) Milano");
        Printer.println("3) Venezia");
        Printer.println("4) Nessun filtro");

        int scelta;
        do {
            Printer.print("Scelta: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 4);

        return switch (scelta) {
            case 1 -> "Roma";
            case 2 -> "Milano";
            case 3 -> "Venezia";
            default -> "Località";
        };
    }

    private String getTipoEvento(Scanner in) {
        Printer.println("Seleziona il tipo di evento:");
        Printer.println("1) Concerto");
        Printer.println("2) Teatro");
        Printer.println("3) Cinema");
        Printer.println("4) Nessun filtro");

        int scelta;
        do {
            Printer.print("Scelta: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 4);

        return switch (scelta) {
            case 1 -> "Concerto";
            case 2 -> "Teatro";
            case 3 -> "Cinema";
            default -> "Eventi";
        };
    }

    private LocalDateTime getDataEvento(Scanner in) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        Printer.print("Inserisci la data che ti interessa (YYYY-MM): ");
        String input = in.next();
        YearMonth yearMonth = YearMonth.parse(input, formatter);
        return yearMonth.atDay(1).atStartOfDay();
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------CERCA e PRENOTA EVENTI--------------");
    }

}
