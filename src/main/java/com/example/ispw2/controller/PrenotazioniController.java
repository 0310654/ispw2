package com.example.ispw2.controller;

import com.example.ispw2.DAO.PrenotazioniDAO;
import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.model.Cliente;
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

    public ArrayList<Prenotazione> getPrenotazioni() {
        PrenotazioniDAO prenotazioniDAO = DAOFactory.getDAOFactory().getPrenotazioniDAO();
        ArrayList<Prenotazione> prenotazioni =  prenotazioniDAO.getPrenotazioni();
        return prenotazioni;
    }

    public String newCodice() {

        ArrayList<Prenotazione> prenotazioni =  getPrenotazioni();

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

            PrenotazioniDAO prenotazioniDAO = DAOFactory.getDAOFactory().addPrenotazioniDAO();
            prenotazioniDAO.addPrenotazione(prenotazione);

            //ho aggiunto la prenotazione tra le prenotazioni pendenti del cliente
            ((Cliente) LoginController.getInstance().getUser()).setPrenotazionePendente(prenotazione);

        }else{
            throw new MaxPendingBorrowsException();
        }
    }

    public Prenotazione getPrenotazione(){
        return this.prenotazione;
    }
}
