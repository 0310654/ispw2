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
        if(eventi == null) {
            EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
            eventi = eventiDAO.getEventi();
        }

        eventi_filtrati = new ArrayList<>();
        double[] rangePrezzo = parsePrezzo(selectedBean.getPrezzo());
        YearMonth selectedYearMonth = selectedBean.getData() != null ? YearMonth.from(selectedBean.getData()) : null;

        for (Evento evento : eventi) {
            if (matchTipo(evento, selectedBean) &&
                    matchLocalita(evento, selectedBean) &&
                    matchData(evento, selectedYearMonth) &&
                    matchPrezzo(evento, rangePrezzo)) {

                eventi_filtrati.add(evento);
            }
        }

        return eventi_filtrati;
    }

    private double[] parsePrezzo(String prezzoSelezionato) {
        double prezzoMin = 0;
        double prezzoMax = Double.MAX_VALUE;

        if (prezzoSelezionato != null && prezzoSelezionato.matches("\\d+(?:\\.\\d+)?€-\\d+(?:\\.\\d+)?€")) {
            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)€-(\\d+(?:\\.\\d+)?)€");
            Matcher matcher = pattern.matcher(prezzoSelezionato);
            if (matcher.find()) {
                prezzoMin = Double.parseDouble(matcher.group(1));
                prezzoMax = Double.parseDouble(matcher.group(2));
            }
        }

        return new double[]{prezzoMin, prezzoMax};
    }

    private boolean matchTipo(Evento evento, SelectedBean selectedBean) {
        return selectedBean.getTipoEvento().equals("Eventi") || selectedBean.getTipoEvento().equals(evento.getTipo_evento());
    }

    private boolean matchLocalita(Evento evento, SelectedBean selectedBean) {
        return selectedBean.getLocalita().equals("Località") || selectedBean.getLocalita().equals(evento.getLocalita());
    }

    private boolean matchData(Evento evento, YearMonth selectedYearMonth) {
        return selectedYearMonth == null || YearMonth.from(evento.getData_evento()).equals(selectedYearMonth);
    }

    private boolean matchPrezzo(Evento evento, double[] rangePrezzo) {
        double prezzoMin = rangePrezzo[0];
        double prezzoMax = rangePrezzo[1];

        for (Double prezzoSettore : evento.getPrezzo_settore()) {
            if (prezzoMin == 0 || (prezzoSettore >= prezzoMin && prezzoSettore <= prezzoMax)) {
                return true;
            }
        }
        return false;
    }


}
