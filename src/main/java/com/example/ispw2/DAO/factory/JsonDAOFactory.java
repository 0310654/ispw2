package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.*;

public class JsonDAOFactory extends DAOFactory {

    @Override
    public UserDAO createUserDAO() {
        return new UserJSONDAO();
    }

    @Override
    public EventiDAO getEventiDAO() {
        return null;
    }

    @Override
    public EventiDAO addEventiDAO() {
        return null;
    }

    @Override
    public PrenotazioniDAO addPrenotazioniDAO() {
        return null;
    }

    @Override
    public PrenotazioniDAO getPrenotazioniDAO() {
        return null;
    }

    /*@Override
    public SettoreMySQLDAO getNumMaxSettoriDAO() {
        return null;
    }*/
}
