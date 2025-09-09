package com.example.ispw2.engineering.factory;

//import com.example.ispw2.DAO.*;
import com.example.ispw2.engineering.DAO.*;
import com.example.ispw2.engineering.DAO.mysql.EventiMySQLDAO;
import com.example.ispw2.engineering.DAO.mysql.PrenotazioniMySQLDAO;
import com.example.ispw2.engineering.DAO.mysql.UserMySQLDAO;

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
    public PrenotazioniDAO getPrenotazioniDAO() {
        return new PrenotazioniMySQLDAO();
    }


}
