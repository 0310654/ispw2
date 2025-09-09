package com.example.ispw2.engineering.query;

import com.example.ispw2.model.Cliente;

import java.sql.*;

public class LoginQuery {

    private LoginQuery() {}

    public static boolean checkEmailReg(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_EMAIL);

        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        return !rs.next();

    }

    public static int nuovoCliente(Connection conn, Cliente cliente) throws SQLException{

        PreparedStatement stmt = conn.prepareStatement(Query.REGISTER_USER);

        stmt.setString(1, cliente.getEmail());
        stmt.setString(2, cliente.getPassword());
        stmt.setString(3, cliente.getName());
        stmt.setString(4, cliente.getSurname());

        return stmt.executeUpdate();
    }

    public static ResultSet loginUser(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_USER);

        stmt.setString(1, email);
        return stmt.executeQuery();

    }

    public static ResultSet loadCliente(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_CLIENTE);

        stmt.setString(1, email);
        return stmt.executeQuery();

    }

    public static ResultSet loadOrganizzatore(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_ORGANIZZATORE);

        stmt.setString(1, email);
        return stmt.executeQuery();
    }

    public static ResultSet getPrenotazionePendente(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_PRENOTAZIONE);

        stmt.setString(1, email);
        return stmt.executeQuery();
    }

    public static ResultSet getImieiEventi(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_EVENTO);

        stmt.setString(1, email);
        return stmt.executeQuery();
    }

    public static ResultSet getSettori(Connection conn, String cod_evento) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_SETTORE);

        stmt.setString(1, cod_evento);
        return stmt.executeQuery();
    }




}
