package com.example.ispw2.DAO.factory;

import com.example.ispw2.DAO.EventiDAO;
import com.example.ispw2.DAO.PrenotazioniDAO;
import com.example.ispw2.DAO.SettoreMySQLDAO;
import com.example.ispw2.DAO.UserDAO;
import com.example.ispw2.altro.Printer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {
    private static DAOFactory me = null;

    protected DAOFactory(){
    }

    /** Recupera dal file config.properties il tipo di persistenza utilizzata,
     * se non è possibile come default viene utilizzato MYSQL */

    public static synchronized void refreshDAOFactory(Boolean demoMode){
        if(demoMode){
            Properties properties = new Properties();

            try (InputStream input = new FileInputStream("src/main/resources/configurations.properties")) {
                properties.load(input);
                System.out.println(properties.toString());
            } catch (IOException e){
                Printer.errorPrint(e.getMessage());
            }

            String persistenceType = properties.getProperty("PERSISTENCE_TYPE");

            if (persistenceType.equalsIgnoreCase("JSON")) {
                System.out.println("Using JSON Persistence");
                me = new JsonDAOFactory();
            }else if(persistenceType.equalsIgnoreCase("MySQL")){
                System.out.println("Using MySQL Persistence");
                me = new MySQLDAOFactory();
            }else if (persistenceType.equalsIgnoreCase("demo")){
                System.out.println("Using Demo Persistence");
                me = new DemoDAOFactory();
            }else{
                System.out.println("Unknown Persistence Type: " + persistenceType);
                throw new RuntimeException("Unknown persistence type: " + persistenceType);
            }
        }
    }

    public static synchronized DAOFactory getDAOFactory(){
        if ( me == null ){
            Properties properties = new Properties();

            try (InputStream input = new FileInputStream("src/main/resources/configurations.properties")) {
                properties.load(input);
                System.out.println(properties.toString());
            } catch (IOException e){
                Printer.errorPrint(e.getMessage());
            }

            String persistenceType = properties.getProperty("PERSISTENCE_TYPE");

            if (persistenceType.equalsIgnoreCase("JSON")) {
                System.out.println("Using JSON Persistence");
                me = new JsonDAOFactory();
            }else if(persistenceType.equalsIgnoreCase("MySQL")){
                System.out.println("Using MySQL Persistence");
                me = new MySQLDAOFactory();
            }else if (persistenceType.equalsIgnoreCase("demo")){
                System.out.println("Using Demo Persistence");
                me = new DemoDAOFactory();
            }else{
                System.out.println("Unknown Persistence Type: " + persistenceType);
                throw new RuntimeException("Unknown persistence type: " + persistenceType);
            }
        }
        return me;
    }

    public abstract UserDAO createUserDAO();

    public abstract EventiDAO getEventiDAO();

    //public abstract EventiDAO addEventiDAO();

    //public abstract PrenotazioniDAO addPrenotazioniDAO();

    public abstract PrenotazioniDAO getPrenotazioniDAO();
}
