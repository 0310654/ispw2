package com.example.ispw2.query;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Query {


    private Query(){}

    /*--------------------Register Queries-------------------*/

    //Query per verificare la presenza della mail nel db
    public static final String SEARCH_EMAIL = "SELECT * FROM ispw.User WHERE email = ?";

    //query per registrare il costumer sia nella tabella user che costumer
    public static final String REGISTER_USER = "INSERT INTO ispw.User (email, password, nome, cognome, tipo_user, data_registrazione) VALUES (?,?,?,?,'utente registrato',CURDATE())";



    /*--------------------Login Queries-----------------------------*/

    //Query per ottenere le info dell'utente nel db
    public static final String GET_USER = "SELECT * FROM ispw.User WHERE email = ?";

    //query per il load delle info del cliente (prendo le info da User e Prenotazioni per le prenotazioni pendenti)
    public static final String GET_CLIENTE =  """
            SELECT
                u.email,
                u.password,
                u.nome,
                u.cognome,
                u.tipo_user,
                u.data_registrazione,
                p.cod_evento,
                p.cod_prenotazione,
                p.data_prenotazione,
                p.stato_prenotazione
                FROM 
                    ispw.User u
                LEFT JOIN 
                    ispw.Prenotazione p ON u.email = p.email AND p.stato_prenotazione = 'PENDING' 
                WHERE 
                    u.email = ?;
        """;

    //query per il load delle info dell'organizzatore
    public static final String GET_ORGANIZZATORE =   """
            SELECT
                u.email,
                u.password,
                u.nome,
                u.cognome,
                u.tipo_user,
                u.data_registrazione,
                e.cod_evento,
                e.nome_evento,
                e.ente,
                e.tipo_evento,
                e.localita,
                e.data_evento,
                e.descrizione_evento
                FROM 
                    ispw.User u
                LEFT JOIN 
                    ispw.Evento e ON u.email = e.email_organizzatore'
                WHERE 
                    u.email = ?;
        """;

    public static final String GET_PRENOTAZIONE = """
            SELECT
                e.nome_evento,
                p.cod_prenotazione,
                u.nome,
                u.cognome,
                e.data_evento,
                p.data_prenotazione,
                p.stato_prenotazione
            FROM
                ispw.User u
            INNER JOIN
                ispw.Prenotazione p ON u.email = p.email AND p.stato_prenotazione = 'PENDING'
            INNER JOIN
                ispw.Evento e ON p.cod_evento = e.cod_evento
            WHERE u.email = ?
""";

    public static final String GET_EVENTO = """
           SELECT 
                e.cod_evento,
                e.nome_evento,
                e.email_organizzatore,
                e.ente,
                e.tipo_evento,
                e.localita,
                e.data_evento,
                e.descrizione_evento
            FROM
                ispw.Evento e
            WHERE 
                e.email_organizzatore = ?

            """;


    public static final String GET_SETTORE = """
          SELECT
               s.nome,
               s.disponibilita,
               s.prezzo,
               s.numero_posti
           FROM
               ispw.Evento e
           LEFT JOIN
               ispw.Settori s ON s.codice_evento = e.cod_evento
           WHERE 	
                e.cod_evento = ?

            """;


    /*--------------------Ricerca Queries Cliente-------------------*/

    public static final String SHOW_CATALOG = "SELECT * FROM ispw.Evento";

    public static final String SHOW_PRENOTAZIONI = "SELECT * FROM ispw.Prenotazione";



    /*--------------------Ricerca Queries Organizzatore-------------------*/

    public static final String ADD_EVENT = "INSERT INTO ispw.Evento VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_SETTORE = "INSERT INTO ispw.Settori VALUES (?, ?, ?, ?, ?, ?)"; ;



    /*--------------------Ricerca Queries Prenotazioni-------------------*/

    public static final String GET_NAME = "SELECT u.nome FROM ispw.User u WHERE u.email = ?";
    public static final String GET_SURNAME = "SELECT u.cognome FROM ispw.User u WHERE u.email = ?";

    public static final String GET_NOME_EVENTO = "SELECT e.nome_evento FROM ispw.Evento e WHERE e.cod_evento = ?";
    public static final String  GET_DATA_EVENTO = "SELECT e.data_evento FROM ispw.Evento e WHERE e.cod_evento = ?";

    public static final String ADD_PRENOTAZIONE = "INSERT INTO ispw.Prenotazione VALUES (?, ?, ?, ?, ?)";
    public static final String GET_COD_EVENTO = "SELECT e.cod_evento FROM ispw.Evento e WHERE e.nome_evento = ?";
    public static final String GET_EMAIL_USER = "SELECT u.email FROM ispw.User u WHERE u.nome = ? AND u.cognome = ?";

    public static final String GET_MAX_CODE_SETTORE = """
            SELECT codice_settore AS max_cod
                FROM ispw.Settori
                ORDER BY CAST(SUBSTRING(codice_settore, 2) AS UNSIGNED) DESC
                LIMIT 1
            """;
}
