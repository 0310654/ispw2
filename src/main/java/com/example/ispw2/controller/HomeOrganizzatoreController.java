package com.example.ispw2.controller;

import com.example.ispw2.engineering.DAO.EventiDAO;
import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.model.Evento;

import java.util.ArrayList;

public class HomeOrganizzatoreController {

    private static HomeOrganizzatoreController instance;

    private HomeOrganizzatoreController() {}
    public static HomeOrganizzatoreController getInstance() {
        if(instance == null) {
            instance = new HomeOrganizzatoreController();
        }
        return instance;
    }


    private ArrayList<Evento> iMieiEventi;
    String emailOrganizzatore = LoginController.getInstance().getUser().getEmail();

    public ArrayList<Evento> getMieiEventi() {

        EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        ArrayList<Evento> eventi = eventiDAO.getEventi();

        iMieiEventi = new ArrayList<>();
        for(Evento evento : eventi) {
            if(evento.getOrganizzatore().equals(emailOrganizzatore)) {
                iMieiEventi.add(evento);
            }
        }
        return iMieiEventi;
    }

}
