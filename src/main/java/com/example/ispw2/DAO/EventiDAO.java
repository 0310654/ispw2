package com.example.ispw2.DAO;

import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;

import java.io.IOException;
import java.util.ArrayList;

public interface EventiDAO {

    void addEvento(Evento evento);

    ArrayList<Evento> getEventi();

}
