package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.EventiDAO;
import com.example.ispw2.DAO.PrenotazioniDAO;
import com.example.ispw2.DAO.SettoreMySQLDAO;
import com.example.ispw2.DAO.UserDAO;
import com.example.ispw2.altro.Printer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {
    private static DAOFactory me = null;

    protected DAOFactory(){
    }

    /** Recupera dal file config.properties il tipo di persistenza utilizzata,
     * se non è possibile come default viene utilizzato MYSQL */

    public static synchronized DAOFactory getDAOFactory(){
        if ( me == null ){
            Properties properties = new Properties();

            try (InputStream input = DAOFactory.class.getClassLoader().getResourceAsStream("configurations.properties")) {
                properties.load(input);
            } catch (IOException e){
                Printer.errorPrint(e.getMessage());
            }

            String persistenceType = properties.getProperty("PERSISTENCE_TYPE", "MYSQL");

            if (persistenceType.equalsIgnoreCase("JSON")) {
                me = new JsonDAOFactory();
            }else if(persistenceType.equalsIgnoreCase("MySQL")){
                me = new MySQLDAOFactory();
            }else if (persistenceType.equalsIgnoreCase("demo")){
                me = new DemoDAOFactory();
            }else{
                me = new MySQLDAOFactory();
            }
        }
        return me;
    }

    public abstract UserDAO createUserDAO();

    public abstract EventiDAO getEventiDAO();

    public abstract EventiDAO addEventiDAO();

    public abstract PrenotazioniDAO addPrenotazioniDAO();

    public abstract PrenotazioniDAO getPrenotazioniDAO();

    //public abstract SettoreMySQLDAO getNumMaxSettoriDAO();
}
