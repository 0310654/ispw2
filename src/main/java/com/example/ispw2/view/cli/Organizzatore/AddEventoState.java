package com.example.ispw2.view.cli.Organizzatore;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.bean.EventBean;
import com.example.ispw2.controller.AddEventoController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AddEventoState extends State {
    private Organizzatore organizzatore;
    public AddEventoState(Organizzatore organizzatore) {
        super();
        this.organizzatore = organizzatore;
    }
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(StateMachine stateMachine) {
        Printer.print("Inserisci il nome dell'evento: ");
        String nome_evento = scanner.nextLine();
        Printer.print("Inserisci il nome dell'ente: ");
        String ente = scanner.next();
        Printer.print("Inserisci il tipo di evento: ");
        String tipo_evento = scanner.next();
        Printer.print("Inserisci la località dell'evento: ");
        String localita = scanner.next();

        ArrayList<String> settore = inserisciSettori(scanner);
        ArrayList<Integer> posti = inserisciPostiSettori(scanner);
        ArrayList<Double> prezzi = inserisciPrezziSettori(scanner);

        Printer.print("Inserisci una descrizione per l'evento: ");
        String descrizione = scanner.nextLine();
        String codice = AddEventoController.getInstance().newCodiceEvento();
        String organizzatore = LoginController.getInstance().getUser().getEmail();

        LocalDateTime data = getData(scanner);

        EventBean eventBean = new EventBean(codice,
                nome_evento,
                organizzatore,
                ente,
                tipo_evento,
                localita,
                settore,
                posti,
                prezzi,
                data,
                posti,
                descrizione);

        AddEventoController.getInstance().addEvento(eventBean);
        Printer.println("Evento registrato con successo!");
        stateMachine.goBack();
    }

    private LocalDateTime getData(Scanner in) {
        Printer.print("Inserisci la data e l'orario di inizio dell'evento (YYYY-MM-DD HH:mm): ");
        String input = in.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(input, formatter);
    }

    private ArrayList<String> inserisciSettori(Scanner scanner) {
        ArrayList<String> settori = new ArrayList<>();
        while (true) {
            System.out.print("Inserisci il nome del settore (o 'fine' per terminare): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("fine")) {
                break;
            }
            if (!input.isEmpty()) {
                settori.add(input);
            } else {
                System.out.println("⚠️ Inserisci un nome valido!");
            }
        }
        return settori;
    }

    private ArrayList<Integer> inserisciPostiSettori(Scanner scanner) {
        ArrayList<Integer> posti = new ArrayList<>();
        while (true) {
            System.out.print("Inserisci il numero di posti del settore corrispondente (o 'fine' per terminare): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("fine")) {
                break;
            }
            if (!input.isEmpty()) {
                posti.add(Integer.valueOf(input));
            } else {
                System.out.println("⚠️ Inserisci un numero valido!");
            }
        }
        return posti;
    }

    private ArrayList<Double> inserisciPrezziSettori(Scanner scanner) {
        ArrayList<Double> prezzi = new ArrayList<>();
        while (true) {
            System.out.print("Inserisci il prezzo del settore corrispondente (o 'fine' per terminare): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("fine")) {
                break;
            }
            if (!input.isEmpty()) {
                prezzi.add(Double.valueOf(input));
            } else {
                System.out.println("⚠️ Inserisci un numero valido!");
            }
        }
        return prezzi;
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------AGGIUNGI UN EVENTO--------------");
    }


}
