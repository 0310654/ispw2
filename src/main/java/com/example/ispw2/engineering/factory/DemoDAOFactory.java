package com.example.ispw2.engineering.factory;

//import com.example.ispw2.DAO.*;
import com.example.ispw2.engineering.DAO.*;
import com.example.ispw2.engineering.DAO.demo.DemoEventiDAO;
import com.example.ispw2.engineering.DAO.demo.DemoPrenotazioneDAO;
import com.example.ispw2.engineering.DAO.demo.DemoUserDAO;

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
