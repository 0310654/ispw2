package com.example.ispw2.controller;

import com.example.ispw2.DAO.DemoEventiDAO;
import com.example.ispw2.DAO.DemoPrenotazioneDAO;
import com.example.ispw2.DAO.EventiDAO;
import com.example.ispw2.DAO.SettoreMySQLDAO;
import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.DAO.factory.MySQLDAOFactory;
import com.example.ispw2.bean.EventBean;
import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.Prenotazione;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddEventoController {

    private static AddEventoController instance;
    private Evento evento;

    private AddEventoController() {}
    public static AddEventoController getInstance() {
        if(instance == null) {
            instance = new AddEventoController();
        }
        return instance;
    }


    public String newCodiceEvento() {

        EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        ArrayList<Evento> eventi = eventiDAO.getEventi();
        ArrayList<Integer> codici = new ArrayList<>();
        for (Evento evento : eventi) {
            try {
                codici.add(Integer.valueOf(String.valueOf(Integer.parseInt(String.valueOf(evento.getCodice())))));
            } catch (NumberFormatException e) {
                System.out.println("Errore nel parsing del codice: " + evento.getCodice());
            }
        }

        if (codici.isEmpty()) {
            return "001";
        }

        int maxCodice = codici.stream().max(Integer::compareTo).orElse(1000);
        int nuovoCodice = maxCodice + 1;

        return String.valueOf(nuovoCodice);
    }

    public void addEvento(EventBean eventBean) {
        this.evento = new Evento( eventBean.getCodice(),
                eventBean.getNome(),
                eventBean.getOrganizzatore(),
                eventBean.getEnte(),
                eventBean.getTipo_evento(),
                eventBean.getLocalita(),
                eventBean.getSettore(),
                eventBean.getDisponibilita_settore(),
                eventBean.getPrezzo_settore(),
                eventBean.getData_evento(),
                eventBean.getNum_posti_settore(),
                eventBean.getDescrizione());

        EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        try {
            eventiDAO.addEvento(evento);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ((Organizzatore)LoginController.getInstance().getUser()).addiMieiEventi(evento);
    }

    //TODO vedere se serve qui
    /*public String newCodiceSettore() {

        SettoreMySQLDAO settoreMySQLDAO = DAOFactory.getDAOFactory().getNumMaxSettoriDAO();
        String cod_max = settoreMySQLDAO.getNumMaxSettoriDAO();

        String prefisso = cod_max.substring(0, 1);
        String parteNumerica = cod_max.substring(1);
        int numero = Integer.parseInt(parteNumerica);
        if(numero == 0){
            return "S001";
        }
        numero++;
        String nuovoCodice = String.format("%s%03d", prefisso, numero);

        return nuovoCodice;
    }*/

}
