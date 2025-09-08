package com.example.ispw2.controller;

import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.DAO.UserDAO;
import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.RegisterBean;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.User;
import com.example.ispw2.view.gui.other.Configurations;

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

            /*creo costumer a partire dai dati del bean
            Cliente cliente = new Cliente(regBean.getEmail(),
                    regBean.getPassword(),
                    regBean.getName(),
                    regBean.getSurname(),
                    null,
                    null);*/
            regBean.setData_registrazione(LocalDate.now());
            regBean.setPrenotazione_pendente(null);
            //chiamo la DAO per la registrazione del costumer
            try {
                //nello userDAO
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