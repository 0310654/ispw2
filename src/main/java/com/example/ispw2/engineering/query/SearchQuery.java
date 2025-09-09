package com.example.ispw2.engineering.query;

import java.sql.*;
import java.time.LocalDateTime;

public class SearchQuery {

    private SearchQuery(){}


    public static ResultSet showCatalog(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.SHOW_CATALOG);
        return stmt.executeQuery();
    }


    public static void addEvent(Connection conn, String codice, String nome, String organizzatore, String ente, String tipo_evento, String localita, LocalDateTime data_evento, String descrizione) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.ADD_EVENT);

        stmt.setString(1, codice);
        stmt.setString(2, nome);
        stmt.setString(3, organizzatore);
        stmt.setString(4, ente);
        stmt.setString(5, tipo_evento);
        stmt.setString(6, localita);
        stmt.setString(7, data_evento.toString());
        stmt.setString(8, descrizione);

        System.out.println(codice + "|"+ nome+ "|"+ organizzatore+ "|"+ente+ "|"+tipo_evento+ "|"+localita+ "|"+data_evento+ "|"+descrizione + "|");
        if(stmt.executeUpdate()==0){
            throw new SQLException();
        }
    }

    public static void addSettore(Connection conn,
                                  String codice_evento,
                                  String codice_settore,
                                  String nome,
                                  Integer numero_posti,
                                  Double prezzo,
                                  Integer disponibilita) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.ADD_SETTORE);

        stmt.setString(1, codice_evento);
        stmt.setString(2, codice_settore);
        stmt.setString(3, nome);
        stmt.setString(4, numero_posti.toString());
        stmt.setString(5, prezzo.toString());
        stmt.setString(6, disponibilita.toString());

        System.out.println(codice_evento+ "|"+ codice_settore+ "|"+nome+ "|"+numero_posti+ "|"+prezzo+ "|"+disponibilita+ "|");
        if(stmt.executeUpdate()==0){
            throw new SQLException();
        }
    }

    public static ResultSet getMaxCodSettore(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_MAX_CODE_SETTORE);
        return stmt.executeQuery();
    }

}
