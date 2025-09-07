package com.example.ispw2.DAO;

import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Prenotazione;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DemoPrenotazioneDAO implements PrenotazioniDAO {

    private static DemoPrenotazioneDAO instance;
    public static DemoPrenotazioneDAO getInstance() {
        if (instance == null) {
            instance = new DemoPrenotazioneDAO();
        }
        return instance;
    }

    ArrayList<Prenotazione> prenotazioni = new ArrayList<>();



    public DemoPrenotazioneDAO() {
        prenotazioni.add(new Prenotazione("Concerto Coldplay","1001", "Luca", "Bianchi", LocalDateTime.of(2025, 9, 10, 21, 0),LocalDateTime.of(2025, 8, 25, 14, 15)
                ,"CONFERMATA"));
        prenotazioni.add(new Prenotazione("Mostra Van Gogh", "1002", "Maria", "Rossi", LocalDateTime.of(2025, 9, 12, 10, 30),LocalDateTime.of(2025, 8, 25, 14, 15)
                ,"PENDENTE"));
        prenotazioni.add(new Prenotazione("Opera La Traviata","1003", "Marco", "Verdi", LocalDateTime.of(2025, 9, 25, 20, 30),LocalDateTime.of(2025, 8, 25, 14, 15)
                ,"ANNULLATA"));
        prenotazioni.add(new Prenotazione("Opera La Traviata","1004", "Giulia", "Neri", LocalDateTime.of(2025, 9, 25, 20, 30),LocalDateTime.of(2025, 8, 25, 14, 15)
                ,"CONFERMATA"));
        prenotazioni.add(new Prenotazione("Concerto Coldplay","1005", "Andrea", "Galli", LocalDateTime.of(2025, 9, 10, 21, 0),LocalDateTime.of(2025, 8, 25, 14, 15)
                ,"PENDENTE"));
    }

    @Override
    public ArrayList<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    @Override
    public void addPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
    }
}
