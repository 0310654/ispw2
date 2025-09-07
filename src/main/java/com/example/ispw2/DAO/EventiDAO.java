package com.example.ispw2.DAO;

import com.example.ispw2.model.Evento;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EventiDAO {


    void addEvento(Evento evento) throws SQLException;

    ArrayList<Evento> getEventi();

}
