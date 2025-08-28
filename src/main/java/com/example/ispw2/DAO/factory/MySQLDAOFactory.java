package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.UserDAO;
import com.example.ispw2.DAO.UserMySQLDAO;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public UserDAO createUserDAO() {
        return new UserMySQLDAO();
    }
}
