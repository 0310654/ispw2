package com.example.ispw2.view.cli.Organizzatore;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.bean.EventBean;
import com.example.ispw2.controller.HomeOrganizzatoreController;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.util.ArrayList;
import java.util.List;

public class ImieiEventiState extends State {

    private Organizzatore organizzatore;

    public ImieiEventiState(Organizzatore organizzatore) {
        super();
        this.organizzatore = organizzatore;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        ArrayList<Evento> imieieventi = HomeOrganizzatoreController.getInstance().getEventi();
        printEventi(imieieventi);
    }

    private void printEventi(ArrayList<Evento> eventiFiltrati) {
        int i = 1;
        for (Evento evento : eventiFiltrati) {
            Printer.print(i++ + ") " + evento.toString());
        }
    }
}
