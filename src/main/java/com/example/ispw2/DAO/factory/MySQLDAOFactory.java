package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.*;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public UserDAO createUserDAO() {
        return new UserMySQLDAO();
    }

    @Override
    public EventiDAO getEventiDAO() {
        return new EventiMySQLDAO();
    }

    @Override
    public EventiDAO addEventiDAO() {
        return new EventiMySQLDAO();
    }

    @Override
    public PrenotazioniDAO addPrenotazioniDAO() {
        return new PrenotazioniMySQLDAO();
    }

    @Override
    public PrenotazioniDAO getPrenotazioniDAO() {
        return new PrenotazioniMySQLDAO();
    }

    /*@Override
    public SettoreMySQLDAO getNumMaxSettoriDAO() {
        return new SettoreMySQLDAO();
    }*/

}
