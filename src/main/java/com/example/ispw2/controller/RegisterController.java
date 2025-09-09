package com.example.ispw2.controller;

import com.example.ispw2.engineering.DAO.UserDAO;
import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.bean.RegisterBean;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.EmailGiaInUsoException;
import com.example.ispw2.altro.configurations.Configurations;

import java.time.LocalDate;
import java.util.logging.Logger;

public class RegisterController {

    private static RegisterController instance;

    private RegisterController() {}
    public static RegisterController getInstance() {
        if(instance == null) {
            instance = new RegisterController();
        }
        return instance;
    }

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public boolean registraCliente(RegisterBean regBean) throws EmailGiaInUsoException, DAOException {

        if(regBean != null) {
            UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();

            regBean.setData_registrazione(LocalDate.now());
            regBean.setPrenotazione_pendente(null);
            try {
                dao.nuovoCliente(regBean);
            } catch (DAOException e) {
                log.severe("Errore in RegisterController: " + e.getMessage());
                Printer.errorPrint("Errore durante la registrazione.");
                throw new DAOException();
            }

            System.out.println("Cliente registrato con successo!");
            return true;
        }

        return false;

    }

}