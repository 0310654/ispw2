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
                    u.email = email_input;
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
                    u.email = email_input;
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
            LEFT JOIN 
                ispw.Prenotazione p ON u.email = p.email AND p.stato_prenotazione = 'PENDING' 
            LEFT JOIN
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



}
