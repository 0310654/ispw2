package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.UserDAO;
import com.example.ispw2.DAO.UserJSONDAO;

public class JsonDAOFactory extends DAOFactory {

    @Override
    public UserDAO createUserDAO() {
        return new UserJSONDAO();
    }
}
