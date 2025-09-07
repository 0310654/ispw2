package com.example.ispw2.controller;

import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.DAO.UserDAO;
import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.User;
import com.example.ispw2.view.gui.other.Configurations;

import java.io.IOException;
import java.util.logging.Logger;

public class LoginController {

    private static LoginController instance;

    private LoginController() {}
    public static LoginController getInstance() {
        if(instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    private User user;

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public User start(LoginBean loginBean) throws CredenzialiErrateException, UserNonTrovatoException, DAOException {

        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();
        LoginBean loggedInBean;
        loggedInBean = dao.getUserInfoByEmail(loginBean.getEmail());

        System.out.println(loggedInBean.getEmail()+ " " + loggedInBean.getPassword());

        if (!loginBean.getPassword().equals(loggedInBean.getPassword())){
            log.severe("Errore nel Login: credenziali errate.");
            throw new CredenzialiErrateException();
        }else{
            if(loggedInBean.getType().equalsIgnoreCase("organizzatore")) {

                this.user = dao.loadOrganizzatore(loggedInBean.getEmail());
                return user;

            } else if (loggedInBean.getType().equalsIgnoreCase("utente registrato")) {

                try {
                    this.user = dao.loadCliente(loggedInBean.getEmail());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return user;
            }
        }

        return null;
    }

    public User getUser() {
        return this.user;
    }


}

