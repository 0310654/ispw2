package com.example.ispw2.controller;

import com.example.ispw2.engineering.DAO.EventiDAO;
import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.engineering.bean.FiltriBean;
import com.example.ispw2.engineering.bean.SelectedBean;
import com.example.ispw2.model.Evento;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeClienteController {

    private static HomeClienteController instance;

    private HomeClienteController() {}
    public static HomeClienteController getInstance() {
        if(instance == null) {
            instance = new HomeClienteController();
        }
        return instance;
    }

    private ArrayList<Evento> eventi;

    public FiltriBean getFiltri() {
        if(eventi == null) {
            EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
            eventi = eventiDAO.getEventi();
        }
        FiltriBean filtriBean = new FiltriBean();

        // Estraggo località e tipi di evento senza duplicati
        for (Evento e : eventi) {
            if (e.getLocalita() != null && !e.getLocalita().isEmpty()) {
                filtriBean.addLocalita(e.getLocalita());
            }
            if (e.getTipo_evento() != null && !e.getTipo_evento().isEmpty()) {
                filtriBean.addEvento(e.getTipo_evento());
            }
        }

        String fascia1 = "0€-50€";
        String fascia2 = "50€-100€";
        String fascia3 = "100€-300€";

        filtriBean.addPrezzo(fascia1);
        filtriBean.addPrezzo(fascia2);
        filtriBean.addPrezzo(fascia3);

        return filtriBean;
    }

    private ArrayList<Evento> eventi_filtrati;
    public ArrayList<Evento> getEventiFiltrati() {
        return eventi_filtrati;
    }

    /**
     * @return Lista di eventi filtrati in base ai filtri selezionati (secondo SelectedBean)
     */
    public ArrayList<Evento> filtriSelezionati(SelectedBean selectedBean) {
        //selectedbean ha una data precisa, io confronto solo il mese e l'anno

        //prendo la lista di tutti gli eventi dal DAO
        if(eventi == null) {
            EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
            eventi = eventiDAO.getEventi();
        }
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

        YearMonth yearMonth = null;
        LocalDateTime confrontData = selectedBean.getData();
        if (confrontData != null) {
            yearMonth = YearMonth.from(confrontData);
        }
        for (Evento evento : eventi) {
            boolean tipoOk = selectedBean.getTipoEvento().equals("Eventi") || selectedBean.getTipoEvento().equals(evento.getTipo_evento());
            boolean localitaOk = selectedBean.getLocalita().equals("Località") || selectedBean.getLocalita().equals(evento.getLocalita());
            LocalDateTime dataEvento = evento.getData_evento();
            YearMonth eventoYM = YearMonth.from(dataEvento);
            boolean dataOk = confrontData == null || yearMonth.equals(eventoYM);

            if (!tipoOk || !localitaOk || !dataOk) {
                continue; // Salta questo evento se non rispetta i filtri base
            }
            // Controlliamo se almeno un settore rientra nel range di prezzo
            boolean settoreValido = false;
            for (Double prezzoSettore : evento.getPrezzo_settore()) {
                if(prezzoMin==0){
                    //non sono stati selezionati filtri di prezzo
                    settoreValido = true;
                }
                else if (prezzoSettore >= prezzoMin && prezzoSettore <= prezzoMax) {
                    settoreValido = true;
                }
            }
            if (settoreValido) {
                eventi_filtrati.add(evento);
            }
        }
        return eventi_filtrati;
    }

}
