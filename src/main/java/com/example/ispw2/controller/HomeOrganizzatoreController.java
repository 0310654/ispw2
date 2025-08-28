package com.example.ispw2.controller;

import com.example.ispw2.DAO.DemoEventiDAO;
import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;

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
    String nomeOrganizzatore = LoginController.getInstance().getUser().getName();

    public ArrayList<Evento> getEventi() {
        ArrayList<Evento> eventi = DemoEventiDAO.getInstance().getEventi();
        iMieiEventi = new ArrayList<>();
        for(Evento evento : eventi) {
            if(evento.getOrganizzatore().equals(nomeOrganizzatore)) {
                iMieiEventi.add(evento);
            }
        }
        return iMieiEventi;
    }

}
