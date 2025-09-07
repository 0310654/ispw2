package com.example.ispw2.DAO;

import com.example.ispw2.model.Prenotazione;

import java.util.ArrayList;

public interface PrenotazioniDAO {

    void addPrenotazione(Prenotazione prenotazione);

    ArrayList<Prenotazione> getPrenotazioni();
}


