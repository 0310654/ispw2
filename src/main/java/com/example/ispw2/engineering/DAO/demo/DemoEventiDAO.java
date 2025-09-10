package com.example.ispw2.engineering.DAO.demo;

import com.example.ispw2.engineering.DAO.EventiDAO;
import com.example.ispw2.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DemoEventiDAO implements EventiDAO {

    private ArrayList<Evento> eventi = new ArrayList<>();


    //SINGLETON

    private static DemoEventiDAO instance;
    public static DemoEventiDAO getInstance() {
        if (instance == null) {
            instance = new DemoEventiDAO();
        }
        return instance;
    }

    private String s1 = "VIP";
    private String s2 = "Standard";
    private String s3 = "Economy";
    private String s4 = "Plate";
    private String s5 = "Galleria";
    private String s6 = "Premium";
    private String s7 = "Base";
    private String s8 = "Front Row";
    private String s9 = "Balconata";

    private String email= "organizzatore@email.com";

    private String l1 = "Roma";
    private String l2 = "Milano";
    private String l3 = "Venezia";

    private String l4 = "Concerto";



    //TODO aggiungere descrizione evento
    public DemoEventiDAO() {
        ArrayList<String> settori1 = new ArrayList<>(List.of(s1, s2, s3));
        ArrayList<Double> prezzi1 = new ArrayList<>(List.of(80.0, 50.0, 30.0));
        ArrayList<Integer> disponibilita1 = new ArrayList<>(List.of(50, 150, 300));

        eventi.add(new Evento("001", "Concerto Coldplay", email, "Comune di Roma",
                l4, l1, settori1, disponibilita1, prezzi1,
                LocalDateTime.of(2025, 9, 10, 21, 0),
                new ArrayList<>(List.of(500, 1000, 2000)), ""));

        ArrayList<String> settori2 = new ArrayList<>(List.of(s4, s5));
        ArrayList<Double> prezzi2 = new ArrayList<>(List.of(25.0, 15.0));
        ArrayList<Integer> disponibilita2 = new ArrayList<>(List.of(80, 120));

        eventi.add(new Evento("002", "Mostra Van Gogh", email, "Museo Milano",
                "Teatro", l2, settori2, disponibilita2, prezzi2,
                LocalDateTime.of(2025, 9, 12, 10, 30),
                new ArrayList<>(List.of(200, 400)), ""));

        ArrayList<String> settori3 = new ArrayList<>(List.of(s3));
        ArrayList<Double> prezzi3 = new ArrayList<>(List.of(60.0));
        ArrayList<Integer> disponibilita3 = new ArrayList<>(List.of(300));

        eventi.add(new Evento("003", "Festival del Cinema", email, "Città di Venezia",
                l4, l3, settori3, disponibilita3, prezzi3,
                LocalDateTime.of(2025, 9, 18, 19, 0),
                new ArrayList<>(List.of(300)), ""));

        ArrayList<String> settori4 = new ArrayList<>(List.of(s6, s7));
        ArrayList<Double> prezzi4 = new ArrayList<>(List.of(40.0, 20.0));
        ArrayList<Integer> disponibilita4 = new ArrayList<>(List.of(60, 200));

        eventi.add(new Evento("004", "Fiera del Libro", email, "Comune di Torino",
                l4, l1, settori4, disponibilita4, prezzi4,
                LocalDateTime.of(2025, 9, 22, 9, 30),
                new ArrayList<>(List.of(200, 500)), ""));

        ArrayList<String> settori5 = new ArrayList<>(List.of(s8, s9));
        ArrayList<Double> prezzi5 = new ArrayList<>(List.of(100.0, 60.0));
        ArrayList<Integer> disponibilita5 = new ArrayList<>(List.of(20, 80));

        eventi.add(new Evento("005", "Opera La Traviata", email, "Comune di Milano",
                "Teatro", l2, settori5, disponibilita5, prezzi5,
                LocalDateTime.of(2025, 9, 25, 20, 30),
                new ArrayList<>(List.of(100, 300)),""));
    }

    @Override
    public ArrayList<Evento> getEventi() {
        return eventi;
    }

    @Override
    public void addEvento(Evento evento) {
        eventi.add(evento);
    }
}

