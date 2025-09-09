package com.example.ispw2.engineering.DAO.mysql;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.DAO.EventiDAO;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Evento;
import com.example.ispw2.engineering.query.LoginQuery;
import com.example.ispw2.engineering.query.SearchQuery;
import com.example.ispw2.altro.configurations.Configurations;
import com.example.ispw2.altro.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EventiMySQLDAO implements EventiDAO {


    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);


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

    private void handleDAOException(Exception e) throws DAOException {
        logger.severe("Error in MakeReservationMySQLDAO: " + e.getMessage());
        Printer.errorPrint("Error occurred making the reservation.");
        throw new DAOException();
    }


    @Override
    public void addEvento(Evento evento)  {
        //con add evento vengono aggiunti anche i settori
        Connection conn = Connector.getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            SearchQuery.addEvent(conn,
                    evento.getCodice(),
                    evento.getNome(),
                    evento.getOrganizzatore(),
                    evento.getEnte(),
                    evento.getTipo_evento(),
                    evento.getLocalita(),
                    evento.getData_evento(),
                    evento.getDescrizione()) ;

            //per un evento posso avere piu settori
            ResultSet rs_cod = SearchQuery.getMaxCodSettore(conn);
            String ultimoCodice;
            if (rs_cod.next()) {
                ultimoCodice = rs_cod.getString("max_cod");
                System.out.println("Ultimo codice = "+ ultimoCodice);
                int volte = evento.getSettore().size();

                for (int i = 0; i < volte; i++) {
                    String nuovoCodice = generaNuovoCodice(String.valueOf(ultimoCodice));
                    ultimoCodice = nuovoCodice;

                    SearchQuery.addSettore(conn,
                            evento.getCodice(),
                            nuovoCodice,
                            evento.getSettore().get(i),
                            evento.getNum_posti_settore().get(i),
                            evento.getPrezzo().get(i),
                            evento.getDisponibilita_settore().get(i));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //unica funzione necessaria solo per la base di dati
    private String generaNuovoCodice(String ultimoCodice) {
        if (ultimoCodice == null) {
            return "S001";
        }
        int numero = Integer.parseInt(ultimoCodice.substring(1));
        numero++;
        return String.format("S%03d", numero);
    }

    @Override
    //da tutti gli eventi
    public ArrayList<Evento> getEventi() {

        ArrayList<Evento> eventi = new ArrayList<>();

        try (ResultSet rs = SearchQuery.showCatalog(Connector.getConnection())) {

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
                        rs.getString("email_organizzatore"),
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

                eventi.add(evento);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return eventi;
    }



}
