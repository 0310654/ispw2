package com.example.ispw2.engineering.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrenotazioniQuery {

    private PrenotazioniQuery(){}

    public static ResultSet showPrenotazioni(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.SHOW_PRENOTAZIONI);
        return stmt.executeQuery();
    }

    public static ResultSet getNomeUser(Connection conn, String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_NAME);
        stmt.setString(1, email);
        return stmt.executeQuery();
    }
    public static ResultSet getCognomeUser(Connection conn, String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_SURNAME);
        stmt.setString(1, email);
        return stmt.executeQuery();
    }
    public static ResultSet getNomeEvento(Connection conn, String cod_evento) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_NOME_EVENTO);
        stmt.setString(1, cod_evento);
        return stmt.executeQuery();
    }
    public static ResultSet getDataEvento(Connection conn,String cod_evento) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_DATA_EVENTO);
        stmt.setString(1, cod_evento);
        return stmt.executeQuery();
    }


    public static void addPrenotazione(Connection conn,
                                       String cod_evento,
                                       String cod_prenotazione,
                                       String email,
                                       String data_prenotazione,
                                       String stato_prenotazione) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.ADD_PRENOTAZIONE);
        stmt.setString(1, cod_evento);
        stmt.setString(2, cod_prenotazione);
        stmt.setString(3, email);
        stmt.setString(4, data_prenotazione);
        stmt.setString(5, stato_prenotazione);
        if(stmt.executeUpdate()==0){
            throw new SQLException();
        }
    }


    public static ResultSet getCodiceEvento(Connection conn, String nome_evento) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_COD_EVENTO);
        stmt.setString(1, nome_evento);
        return stmt.executeQuery();
    }

    public static ResultSet getEmailUser(Connection conn, String nome, String cognome) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_EMAIL_USER);
        stmt.setString(1, nome);
        stmt.setString(2, cognome);
        return stmt.executeQuery();
    }
}
