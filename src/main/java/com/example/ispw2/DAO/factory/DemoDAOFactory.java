package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.DAO.UserDAO;

public class DemoDAOFactory extends DAOFactory {

    @Override
    public UserDAO createUserDAO() {
        return new DemoUserDAO();
    }
}
