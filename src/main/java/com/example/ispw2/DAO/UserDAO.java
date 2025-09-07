package com.example.ispw2.DAO;

import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.Prenotazione;

import java.io.IOException;
import java.util.ArrayList;

public interface UserDAO {

    /** Inserimento dell'utente in persistenza
     * Valore di ritorno booleano per verificare la correttezza dell'operazione */
    void nuovoCliente(Cliente cliente) throws EmailGiaInUsoException, DAOException;

    //TODO non so se levare o meno da qui
    Prenotazione loadPrenotazione(String email) throws UserNonTrovatoException, DAOException;

    /** Recupera le informazioni di un utente in persistenza, ottenuta dall'email */
    Cliente loadCliente(String email) throws UserNonTrovatoException, DAOException, IOException;
    Organizzatore loadOrganizzatore(String email) throws UserNonTrovatoException, DAOException;


    /** Ottiene la password e il ruolo associati all'email */
    LoginBean getUserInfoByEmail(String email) throws UserNonTrovatoException, DAOException;

}
