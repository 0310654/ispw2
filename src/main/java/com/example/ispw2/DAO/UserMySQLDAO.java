package com.example.ispw2.DAO;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.query.LoginQuery;
import com.example.ispw2.view.gui.other.Configurations;
import com.example.ispw2.view.gui.other.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UserMySQLDAO implements UserDAO {

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);


    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNonTrovatoException, DAOException {

        try (ResultSet rs = LoginQuery.loginUser(Connector.getConnection(), email)) {

            if (!rs.next()) {
                throw new UserNonTrovatoException();
            } else {
                return new LoginBean(email, rs.getString("password"), rs.getString("tipo_user"));

            }
        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (getUserInfoByEmail): " + e.getMessage());
            Printer.errorPrint("Errore dando le info user.");
            throw new DAOException();
        }

    }

    @Override
    public void nuovoCliente(Cliente cliente) throws EmailGiaInUsoException, DAOException {
        try{

            Connection conn = Connector.getConnection();

            conn.setAutoCommit(false);

            if(LoginQuery.nuovoCliente(conn, cliente) == 0) {
                throw new DAOException("Error in UserMySQLDAO");
            }

            conn.commit();

        }catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (insertCostumer): " + e.getMessage());
            Printer.errorPrint("Error adding costumer.");
            throw new DAOException();
        }

    }


    @Override
    public Prenotazione loadPrenotazione(String email) throws UserNonTrovatoException, DAOException{
        //trova la prenotazione pendente

        Prenotazione prenotazione;
        try (ResultSet rs = LoginQuery.getPrenotazionePendente(Connector.getConnection(), email)) {

            if (!rs.next()) {
                //throw new UserNonTrovatoException();
                return null;
            }
            else {
                Timestamp tsEvento = rs.getTimestamp("data_evento");
                Timestamp tsPrenotazione = rs.getTimestamp("data_prenotazione");

                System.out.println("prenotazione:" +
                        rs.getString("nome_evento")+ "|"+
                        rs.getString("cod_prenotazione")+ "|"+
                        rs.getString("nome") + "|" +
                        rs.getString("cognome")+ "|" +
                        tsEvento.toLocalDateTime()+ "|" +
                        tsPrenotazione.toLocalDateTime()+ "|" +
                        rs.getString("stato_prenotazione"));

                 prenotazione = new Prenotazione(
                        rs.getString("nome_evento"),
                        rs.getString("cod_prenotazione"),
                         rs.getString("nome"),
                         rs.getString("cognome"),
                         tsEvento != null ? tsEvento.toLocalDateTime() : null,
                         tsPrenotazione != null ? tsPrenotazione.toLocalDateTime() : null,
                         rs.getString("stato_prenotazione"));
            }
        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (loadCostumer): " + e.getMessage());
            Printer.errorPrint("Error loading prenotazione.");
            throw new DAOException();
        }
        return prenotazione;
    }

    @Override
    public Cliente loadCliente(String email) throws UserNonTrovatoException, DAOException {
        Cliente cliente;
        Prenotazione prenotazione = loadPrenotazione(email);

        try (ResultSet rs = LoginQuery.loadCliente(Connector.getConnection(), email)) {

            if (!rs.next()) {

                throw new UserNonTrovatoException();
            } else {
                cliente = new Cliente(email,
                        rs.getString("password"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getDate("data_registrazione").toLocalDate(),
                        prenotazione);
            }
        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (loadCostumer): " + e.getMessage());
            Printer.errorPrint("Error loading costumer.");
            throw new DAOException();
        }

        return cliente;
    }

    @Override
    public Organizzatore loadOrganizzatore(String email) throws UserNonTrovatoException, DAOException {
        Organizzatore organizzatore;
        ArrayList<Evento> iMieiEventi = loadIMieiEventi(email);

        try (ResultSet rs = LoginQuery.loadCliente(Connector.getConnection(), email)) {

            if (!rs.next()) {

                throw new UserNonTrovatoException();
            } else {
                organizzatore = new Organizzatore(email,
                        rs.getString("password"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getDate("data_registrazione").toLocalDate(),
                        iMieiEventi);
            }
        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (loadCostumer): " + e.getMessage());
            Printer.errorPrint("Error loading costumer.");
            throw new DAOException();
        }

        return organizzatore;
    }


    public ArrayList<Evento> loadIMieiEventi(String email) {

        //prende gli eventi organizzati dall'organizzatore in questione (in base all'email dell'organizzatore)

        ArrayList<Evento> iMieiEventi = new ArrayList<>();

        try (ResultSet rs = LoginQuery.getImieiEventi(Connector.getConnection(), email)) {

            if (!rs.isBeforeFirst()) {
                throw new UserNonTrovatoException();
            }

            while (rs.next()) {
                String codEvento = rs.getString("cod_evento");

                Map<String, Object> settoreData = getSettoriByEvento(codEvento);

                @SuppressWarnings("unchecked")
                ArrayList<String> nomiSettori = (ArrayList<String>) settoreData.get("nomi");
                @SuppressWarnings("unchecked")
                ArrayList<Integer> disponibilitaSettori = (ArrayList<Integer>) settoreData.get("disponibilita");
                @SuppressWarnings("unchecked")
                ArrayList<Double> prezzoSettori = (ArrayList<Double>) settoreData.get("prezzi");
                @SuppressWarnings("unchecked")
                ArrayList <Integer> num_postiSettori = (ArrayList<Integer>) settoreData.get("numero_posti");

                Evento evento = new Evento(
                        codEvento,
                        rs.getString("nome_evento"),
                        email,
                        rs.getString("ente"),
                        rs.getString("tipo_evento"),
                        rs.getString("localita"),
                        nomiSettori,
                        disponibilitaSettori,
                        prezzoSettori,
                        rs.getTimestamp("data_evento").toLocalDateTime(),
                        num_postiSettori,
                        rs.getString("descrizione_evento")
                );

                iMieiEventi.add(evento);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return iMieiEventi;
    }

    private Map<String, Object> getSettoriByEvento(String codEvento) throws SQLException {
        ArrayList<String> nomiSettori = new ArrayList<>();
        ArrayList<Integer> disponibilitaSettori = new ArrayList<>();
        ArrayList<Double> prezzoSettori = new ArrayList<>();
        ArrayList<Integer> num_postiSettori = new ArrayList<>();

        try (ResultSet rs = LoginQuery.getSettori(Connector.getConnection(), codEvento)) {
            while (rs.next()) {
                nomiSettori.add(rs.getString("nome"));
                disponibilitaSettori.add(rs.getInt("disponibilita"));
                prezzoSettori.add(rs.getDouble("prezzo"));
                num_postiSettori.add(rs.getInt("numero_posti"));
            }
        }

        Map<String, Object> settoreData = new HashMap<>();
        settoreData.put("nomi", nomiSettori);
        settoreData.put("disponibilita", disponibilitaSettori);
        settoreData.put("prezzi", prezzoSettori);
        settoreData.put("numero_posti", num_postiSettori);

        return settoreData;
    }
}

