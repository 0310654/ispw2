package com.example.ispw2;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.engineering.factory.DemoDAOFactory;
import com.example.ispw2.engineering.factory.JsonDAOFactory;
import com.example.ispw2.engineering.factory.MySQLDAOFactory;
import com.example.ispw2.view.cli.Implementations;
import javafx.application.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("src/main/resources/configurations.properties")) {
            properties.load(input);
        } catch (IOException e){
            Printer.errorPrint(e.getMessage());
        }
        String interfaceType = properties.getProperty("INTERFACE_TYPE");

        if (interfaceType.equalsIgnoreCase("gui")) {
            System.out.println("Using gui Interface");
            Application.launch(HelloApplication.class, args);

        }else if(interfaceType.equalsIgnoreCase("cli")){
            System.out.println("Using cli Interface");
            Implementations implementations = new Implementations();
            implementations.start();
        }else{
            System.out.println("Unknown Interface Type: " + interfaceType);
            throw new RuntimeException("Unknown interface type: " + interfaceType);
        }

    }
}
