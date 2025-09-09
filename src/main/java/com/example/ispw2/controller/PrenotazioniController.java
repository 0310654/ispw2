package com.example.ispw2.controller;

import com.example.ispw2.engineering.DAO.PrenotazioniDAO;
import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.engineering.bean.PrenotazioniBean;
import com.example.ispw2.engineering.exceptions.MaxPendingResException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Prenotazione;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PrenotazioniController {

    private static PrenotazioniController instance;

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }

    private Prenotazione prenotazione;
    private Cliente cliente;

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


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


    public void prenotaEvento(PrenotazioniBean prenotazioneBean) throws MaxPendingResException {

        if(cliente == null) {
            this.cliente = (Cliente) LoginController.getInstance().getUser();
        }
        System.out.println("sono dentro prenotaeventocontroller"+ cliente.toString());

        //controllo che il costumer possa effettuare la prenotazione
        if(cliente.getPrenotazioni_pendenti() == null) {
            //creo model a partire dalla bean

            this.prenotazione = new Prenotazione(prenotazioneBean.getNome_evento(),
                    prenotazioneBean.getCod_prenotazione(),
                    cliente.getName(),
                    cliente.getSurname(),
                    prenotazioneBean.getData_evento(),
                    LocalDateTime.now(),
                    prenotazioneBean.getStato_prenotazione());

            PrenotazioniDAO prenotazioniDAO = DAOFactory.getDAOFactory().getPrenotazioniDAO();
            prenotazioniDAO.addPrenotazione(prenotazione);

            //ho aggiunto la prenotazione tra le prenotazioni pendenti del cliente
            ((Cliente) LoginController.getInstance().getUser()).setPrenotazionePendente(prenotazione);

            System.out.println("ho aggiunto l'evento..."+ cliente.toString());

        }else{
            throw new MaxPendingResException();
        }
    }

    public Prenotazione getPrenotazione(){
        return this.prenotazione;
    }
}
