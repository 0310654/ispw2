package com.example.ispw2.engineering.factory;

//import com.example.ispw2.DAO.*;
import com.example.ispw2.engineering.DAO.EventiDAO;
import com.example.ispw2.engineering.DAO.PrenotazioniDAO;
import com.example.ispw2.engineering.DAO.UserDAO;
import com.example.ispw2.engineering.DAO.json.UserJSONDAO;

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
    public PrenotazioniDAO getPrenotazioniDAO() {
        return null;
    }

}
