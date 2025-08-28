package com.example.ispw2.controller;

import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.bean.RegisterBean;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.User;

import java.time.LocalDate;
import java.util.logging.Logger;

public class RegisterController {

    //private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public boolean registraCliente(RegisterBean regBean) throws EmailGiaInUsoException, DAOException {

        if(regBean != null) {
            DemoUserDAO dao = DemoUserDAO.getInstance();
            //UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();


            //creo costumer a partire dai dati del bean
            Cliente cliente = new Cliente(regBean.getEmail(),
                    regBean.getPassword(),
                    regBean.getName(),
                    regBean.getSurname(),
                    null,
                    null);

            //chiamo la DAO per la registrazione del costumer
            try {
                //nello userDAO
                dao.nuovoCliente(cliente);
            } catch (DAOException e) {
                //log.severe("Errore in RegisterController: " + e.getMessage());
                //Printer.errorPrint("Errore durante la registrazione.");
                throw new DAOException();
            }

            System.out.println("Cliente registrado com sucesso!");
            System.out.println(cliente.toString());
            for(User u: dao.getUsers()){
                System.out.println(u.toString());
            }
            return true;
        }

        return false;

    }

}