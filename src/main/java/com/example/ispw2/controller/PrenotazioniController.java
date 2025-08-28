package com.example.ispw2.controller;

import com.example.ispw2.DAO.DemoPrenotazioneDAO;
import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.FiltriBean;
import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PrenotazioniController {

    private static PrenotazioniController instance;
    private Prenotazione prenotazione;
    private User user;


    private PrenotazioniController() {}
    public static PrenotazioniController getInstance() {
        if(instance == null) {
            instance = new PrenotazioniController();
        }
        return instance;
    }


    public String newCodice() {
        ArrayList<Prenotazione> prenotazioni = DemoPrenotazioneDAO.getInstance().getPrenotazioni();
        ArrayList<Integer> codici = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            try {
                codici.add(Integer.valueOf(String.valueOf(Integer.parseInt(String.valueOf(p.getCod_prenotazione())))));
            } catch (NumberFormatException e) {
                System.out.println("Errore nel parsing del codice: " + p.getCod_prenotazione());
            }
        }

        if (codici.isEmpty()) {
            return "1001";
        }

        int maxCodice = codici.stream().max(Integer::compareTo).orElse(1000);
        int nuovoCodice = maxCodice + 1;

        return String.valueOf(nuovoCodice);
    }

    public void prenotaEvento(PrenotazioniBean prenotazioneBean) throws MaxPendingBorrowsException {
        //controllo che il costumer possa effettuare la prenotazione
        if(!prenotazioneBean.isPrenotazioni_pendenti()) {
            //creo model a partire dalla bean

            this.user = LoginController.getInstance().getUser();
            this.prenotazione = new Prenotazione(prenotazioneBean.getNome_evento(),
                    prenotazioneBean.getCod_prenotazione(),
                    user.getName(),
                    user.getSurname(),
                    prenotazioneBean.getData_evento(),
                    LocalDateTime.now(),
                    prenotazioneBean.getStato_prenotazione());

            DemoPrenotazioneDAO.getInstance().addPrenotazione(prenotazione);
            //ho aggiunto la prenotazione tra le prenotazioni pendenti del cliente
            ((Cliente) LoginController.getInstance().getUser()).setPrenotazionePendente(prenotazione);

        }else{
            throw new MaxPendingBorrowsException();
        }
    }

    public Prenotazione getPrenotazioni(){
        return this.prenotazione;
    }
}
