package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.*;

public class DemoDAOFactory extends DAOFactory {

    @Override
    public UserDAO createUserDAO() {
        return new DemoUserDAO();
    }

    @Override
    public EventiDAO getEventiDAO() {
        return new DemoEventiDAO();
    }

    @Override
    public EventiDAO addEventiDAO() {
        return new DemoEventiDAO();
    }

    @Override
    public PrenotazioniDAO addPrenotazioniDAO() {
        return new DemoPrenotazioneDAO();
    }

    @Override
    public PrenotazioniDAO getPrenotazioniDAO() {
        return new DemoPrenotazioneDAO();
    }

    /*@Override
    public SettoreMySQLDAO getNumMaxSettoriDAO() {
        return null;
    }*/
}
