package com.example.ispw2.engineering.DAO;

import com.example.ispw2.engineering.bean.LoginBean;
import com.example.ispw2.engineering.bean.RegisterBean;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.EmailGiaInUsoException;
import com.example.ispw2.engineering.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.Prenotazione;

import java.io.IOException;

public interface UserDAO {

    /** Inserimento dell'utente in persistenza
     * Valore di ritorno booleano per verificare la correttezza dell'operazione */
    void nuovoCliente(RegisterBean cliente) throws EmailGiaInUsoException, DAOException;

    //TODO non so se levare o meno da qui
    Prenotazione loadPrenotazione(String email) throws UserNonTrovatoException, DAOException;

    /** Recupera le informazioni di un utente in persistenza, ottenuta dall'email */
    Cliente loadCliente(String email) throws UserNonTrovatoException, DAOException, IOException;
    Organizzatore loadOrganizzatore(String email) throws UserNonTrovatoException, DAOException;


    /** Ottiene la password e il ruolo associati all'email */
    LoginBean getUserInfoByEmail(String email) throws UserNonTrovatoException, DAOException;

}
