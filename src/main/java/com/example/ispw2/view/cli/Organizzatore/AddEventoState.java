package com.example.ispw2.view.cli.Organizzatore;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.EventBean;
import com.example.ispw2.controller.AddEventoController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class AddEventoState extends State {
    private Organizzatore organizzatore;
    public AddEventoState(Organizzatore organizzatore) {
        super();
        this.organizzatore = organizzatore;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        var in = new Scanner(System.in);

        String codice = AddEventoController.getInstance().newCodiceEvento();
        String nome_evento = getNomeEvento(in);
        String organizzatore = LoginController.getInstance().getUser().getEmail();
        String ente = getEnte(in);
        String tipo_evento = getTipo_evento(in);
        String localita = getLocalita(in);
        ArrayList<String> settore = getSettore(in);
        ArrayList<Double> prezzi = getPrezzi(in);
        LocalDateTime data = getData(in);
        ArrayList<Integer> posti = getPosti(in);
        String descrizione = getDescr(in);

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
    }

    private String getDescr(Scanner in) {
        Printer.println("Inserisci una descrizione per questo evento: ");
        return in.next();
    }


    private LocalDateTime getData(Scanner in) {
        Printer.print("Inserisci la data dell'evento (YYYY-MM-DD): ");
        String input = in.next();
        return LocalDateTime.parse(input);
    }

    private ArrayList<Double> getPrezzi(Scanner in) {
        ArrayList<Double> prezzi = new ArrayList<>();
        while (in.hasNextLine()) {
            Printer.println("Inserisci il prezzo del relativo settore: ");
            prezzi.add(Double.valueOf(in.nextLine()));
        }
        return prezzi;
    }

    private ArrayList<Integer> getPosti(Scanner in) {
        ArrayList<Integer> dispsettore = new ArrayList<>();
        while (in.hasNextLine()) {
            Printer.println("Inserisci il num di posti nel relativo settore: ");
            dispsettore.add(Integer.valueOf(in.nextLine()));
        }
        return dispsettore;
    }

    private ArrayList<String> getSettore(Scanner in) {
        ArrayList<String> nomisettore = new ArrayList<>();
        while (in.hasNextLine()) {
            Printer.println("Inserisci il nome del settore: ");
            nomisettore.add(in.nextLine());
        }
        return nomisettore;
    }

    private String getLocalita(Scanner in) {
        Printer.println("Inserisci la località dell'evento: ");
        return in.next();
    }

    private String getTipo_evento(Scanner in) {
        Printer.println("Inserisci il tipo dell'evento: ");
        return in.next();
    }

    private String getEnte(Scanner in) {
        Printer.println("Inserisci il nome dell'ente: ");
        return in.next();
    }

    private String getNomeEvento(Scanner in) {
        Printer.println("Inserisci il nome dell'evento: ");
        return in.next();
    }

}
