package com.example.ispw2.controller;

import com.example.ispw2.DAO.DemoUserDAO;
import com.example.ispw2.DAO.DemoEventiDAO;
import com.example.ispw2.DAO.EventiDAO;
import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.bean.FiltriBean;
import com.example.ispw2.bean.SelectedBean;
import com.example.ispw2.model.Evento;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;

public class HomeClienteController {

    private static HomeClienteController instance;

    private HomeClienteController() {}
    public static HomeClienteController getInstance() {
        if(instance == null) {
            instance = new HomeClienteController();
        }
        return instance;
    }

    //TODO rivedere (ce l'ho solo per la demo)
    public FiltriBean getFiltri() {
        FiltriBean filtri = DemoUserDAO.getFiltri();
        return filtri;
    }

    private ArrayList<Evento> eventi_filtrati;
    public ArrayList<Evento> getEventiFiltrati() {
        return eventi_filtrati;
    }

    /**
     * @return Lista di eventi filtrati in base ai filtri selezionati (secondo SelectedBean)
     */
    public ArrayList<Evento> filtriSelezionati(SelectedBean selectedBean) {

        //prendo la lista di tutti gli eventi dal DAO
        EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        ArrayList<Evento> eventi = eventiDAO.getEventi();
        /*System.out.println("tutti gli eventi:");
        for(Evento evento : eventi) {
            System.out.println(evento);
        }*/
        //creo una lista vuota da riempire
        eventi_filtrati = new ArrayList<>();

        double prezzoMin = 0;
        double prezzoMax = Double.MAX_VALUE;

        String prezzoSelezionato = selectedBean.getPrezzo();
        if (prezzoSelezionato != null && prezzoSelezionato.matches("\\d+(?:\\.\\d+)?€-\\d+(?:\\.\\d+)?€")) {
            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)€-(\\d+(?:\\.\\d+)?)€");
            Matcher matcher = pattern.matcher(prezzoSelezionato);

            if (matcher.find()) {
                prezzoMin = Double.parseDouble(matcher.group(1));
                prezzoMax = Double.parseDouble(matcher.group(2));
            }
        }

        for (Evento evento : eventi) {
            boolean tipoOk = selectedBean.getTipoEvento().equals("Eventi") || selectedBean.getTipoEvento().equals(evento.getTipo_evento());
            boolean localitaOk = selectedBean.getLocalita().equals("Località") || selectedBean.getLocalita().equals(evento.getLocalita());

            if (!tipoOk || !localitaOk) {
                continue; // Salta questo evento se non rispetta i filtri base
            }

            // Controlliamo se almeno un settore rientra nel range di prezzo
            boolean settoreValido = false;
            for (Double prezzoSettore : evento.getPrezzo_settore()) {
                if (prezzoSettore >= prezzoMin && prezzoSettore <= prezzoMax) {
                    settoreValido = true;
                    //break; // Basta trovarne uno, non serve continuare
                }
            }

            if (settoreValido) {
                eventi_filtrati.add(evento);
            }
        }
        return eventi_filtrati;
    }

}
