package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.*;

public class DemoDAOFactory extends DAOFactory {

    DemoUserDAO demoUserDAO;
    DemoEventiDAO demoEventiDAO;
    DemoPrenotazioneDAO demoPrenotazioneDAO;

    @Override
    public UserDAO createUserDAO() {
        if(demoUserDAO == null) {
            demoUserDAO = new DemoUserDAO();
        }
        return demoUserDAO;
    }

    @Override
    public EventiDAO getEventiDAO() {
        if(demoEventiDAO == null) {
            demoEventiDAO = new DemoEventiDAO();
        }
        return demoEventiDAO;
    }

    @Override
    public PrenotazioniDAO getPrenotazioniDAO() {
        if(demoPrenotazioneDAO == null) {
            demoPrenotazioneDAO = new DemoPrenotazioneDAO();
        }
        return demoPrenotazioneDAO;
    }

}
