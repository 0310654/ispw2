package com.example.ispw2.DAO;

import com.example.ispw2.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.query.PrenotazioniQuery;
import com.example.ispw2.query.SearchQuery;
import com.example.ispw2.view.gui.other.Configurations;
import com.example.ispw2.view.gui.other.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class PrenotazioniMySQLDAO implements PrenotazioniDAO {

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    @Override
    public void addPrenotazione(Prenotazione prenotazione) {
        String nome_evento = prenotazione.getNome_evento();
        String cod_evento = getCodEvento(nome_evento);
        String nome = prenotazione.getNome();
        String cognome = prenotazione.getCognome();
        String email =  getEmailUser(nome, cognome);

        try {
            PrenotazioniQuery.addPrenotazione(Connector.getConnection(),
                    cod_evento,
                    prenotazione.getCod_prenotazione(),
                    email,
                    String.valueOf(LocalDateTime.now()),
                    "PENDING"
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEmailUser(String nome, String cognome) {
        String email;
        try (ResultSet rs = PrenotazioniQuery.getEmailUser(Connector.getConnection(), nome, cognome)) {
            if(rs.next()) {
                email = rs.getString("email");
                //System.out.println("email :"+ email);
                return email;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private String getCodEvento(String nome_evento) {
        String codice;
        try (ResultSet rs = PrenotazioniQuery.getCodiceEvento(Connector.getConnection(), nome_evento)) {
            if (rs.next()) {
                codice = rs.getString("cod_evento");
                //System.out.println("codice :"+codice);
                return codice;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    //return : tutte le prenotazioni
    public ArrayList<Prenotazione> getPrenotazioni() {

        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();

        try (ResultSet rs = PrenotazioniQuery.showPrenotazioni(Connector.getConnection())) {

            while (rs.next()) {
                String cod_evento = rs.getString("cod_evento");
                String email = rs.getString("email");

                String nome_evento = getNomeEvento(cod_evento);
                LocalDateTime data_evento = getDataEvento(cod_evento);
                String nome = getNomeUser(email);
                String cognome = getCognomeUser(email);


                Prenotazione prenotazione = new Prenotazione(
                        nome_evento,
                        rs.getString("cod_prenotazione"),
                        nome,
                        cognome,
                        data_evento,
                        rs.getTimestamp("data_prenotazione").toLocalDateTime(),
                        rs.getString("stato_prenotazione")
                );

                prenotazioni.add(prenotazione);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prenotazioni;
    }

    private String getNomeUser(String email) {
        String nome;
        try(ResultSet rs = PrenotazioniQuery.getNomeUser(Connector.getConnection(), email)) {
            if(rs.next()) {
                nome = rs.getString("nome");
                //System.out.println("nome user :"+nome);
                return nome;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
        return null;
    }
    private String getCognomeUser(String email) {
        String cognome;
        try(ResultSet rs = PrenotazioniQuery.getCognomeUser(Connector.getConnection(), email)) {
            if(rs.next()) {
                cognome = rs.getString("cognome");
                //System.out.println("cognome user :"+cognome);
                return cognome;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private LocalDateTime getDataEvento(String codEvento) {
        LocalDateTime data_evento;
        try(ResultSet rs = PrenotazioniQuery.getDataEvento(Connector.getConnection(), codEvento)) {
            if (rs.next()) {
                data_evento = rs.getTimestamp("data_evento").toLocalDateTime();
                //System.out.println("data_evento :"+data_evento);
                return data_evento;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private String getNomeEvento(String codEvento) {
        String nome_evento;
        try(ResultSet rs = PrenotazioniQuery.getNomeEvento(Connector.getConnection(), codEvento)) {
            if (rs.next()){
                nome_evento = rs.getString("nome_evento");
                //System.out.println("nome_evento :"+nome_evento);
                return nome_evento;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
        return null;
    }
}
