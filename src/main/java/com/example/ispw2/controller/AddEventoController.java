package com.example.ispw2.controller;

import com.example.ispw2.engineering.DAO.EventiDAO;
import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.engineering.bean.EventBean;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;

import java.sql.SQLException;
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
            System.out.println("Evento salvato:\n"+ evento.toString()+"\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        ((Organizzatore)LoginController.getInstance().getUser()).addiMieiEventi(evento);
    }

}
