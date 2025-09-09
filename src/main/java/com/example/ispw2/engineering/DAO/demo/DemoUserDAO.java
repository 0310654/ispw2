package com.example.ispw2.engineering.DAO.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.DAO.UserDAO;
import com.example.ispw2.engineering.bean.FiltriBean;
import com.example.ispw2.engineering.bean.LoginBean;
import com.example.ispw2.engineering.bean.RegisterBean;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.EmailGiaInUsoException;
import com.example.ispw2.engineering.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.*;
import com.example.ispw2.altro.configurations.Configurations;


public class DemoUserDAO implements UserDAO {


    private ArrayList<User> users = new ArrayList<>();
    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    //SINGLETON

    private static DemoUserDAO instance;
    public static DemoUserDAO getInstance() {
        if (instance == null) {
            instance = new DemoUserDAO();
        }
        return instance;
    }
    public DemoUserDAO() {
        LocalDate data_registrazione = LocalDate.now();

        Prenotazione prenotazione = loadPrenotazione("cliente@email.com");

        users.add(new Cliente("cliente@email.com",
                "password",
                "maria",
                "rossi",
                data_registrazione,
                prenotazione)
        );

        users.add(new Cliente("anna.bianchi@example.com",
                "annaPass!",
                "Anna",
                "Bianchi",
                data_registrazione,
                null));


        ArrayList<String> settori1 = new ArrayList<>(List.of("VIP", "Standard", "Economy"));
        ArrayList<Double> prezzi1 = new ArrayList<>(List.of(80.0, 50.0, 30.0));
        ArrayList<Integer> disponibilita1 = new ArrayList<>(List.of(50, 150, 300));

        ArrayList<Evento> mieiEventi = new ArrayList<>();

        Evento mioEvento = new Evento("001", "Concerto Coldplay", "LiveNation", "Comune di Roma",
                "Concerto", "Roma", settori1, disponibilita1, prezzi1,
                LocalDateTime.of(2025, 9, 10, 21, 0),
                new ArrayList<>(List.of(500, 1000, 2000)), "");

        mieiEventi.add(mioEvento);

        users.add(new Organizzatore("organizzatore@email.com",
                "password",
                "nome",
                "cognome",
                data_registrazione,
                mieiEventi)
        );
    }


    @Override
    public void nuovoCliente(RegisterBean cliente) throws EmailGiaInUsoException, DAOException {
        Cliente client = new Cliente(cliente.getEmail(), cliente.getPassword(), cliente.getName(),cliente.getSurname(), cliente.getData_registrazione(), (Prenotazione) null);


        for (User user : users){
            if(user.getEmail().equals(cliente.getEmail())){
                throw new EmailGiaInUsoException();
            }
        }

        if(!users.add(client)){
            log.severe("Errore in RegisterController: cerco di aggiungere il nuovo cliente alla user list.");
            Printer.errorPrint("Errore durante la registrazione.");
            throw new DAOException();
        }
    }

    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNonTrovatoException {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return new LoginBean(user.getEmail(), user.getPassword(), user.getType());
            }
        }
        throw new UserNonTrovatoException();
    }



    @Override
    public Cliente loadCliente(String email) throws UserNonTrovatoException {
        for(User user: users){
            if(user.getEmail().equals(email)){
                return (Cliente) user;
            }
        }

        throw new UserNonTrovatoException();
    }

    @Override
    public Organizzatore loadOrganizzatore(String email) throws UserNonTrovatoException {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return (Organizzatore) user;
            }
        }

        throw new UserNonTrovatoException();
    }

    public List<User> getUsers() {
        return users;
    }


    @Override
    public Prenotazione loadPrenotazione(String email){
        Prenotazione prenotazione = new Prenotazione(
                "Concerto Coldplay",
                "1001",
                "Luca",
                "Bianchi",
                LocalDateTime.of(2025, 9, 10, 21, 0),
                LocalDateTime.of(2025, 8, 25, 14, 15),
                "CONFERMATA");
        return prenotazione;
    }


}
