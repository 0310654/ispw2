package com.example.ispw2.view.cli.Organizzatore;

import com.example.ispw2.altro.Printer;
import com.example.ispw2.controller.HomeOrganizzatoreController;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.view.cli.State;
import com.example.ispw2.view.cli.StateMachine;

import java.util.ArrayList;

public class ImieiEventiState extends State {

    private Organizzatore organizzatore;

    public ImieiEventiState(Organizzatore organizzatore) {
        super();
        this.organizzatore = organizzatore;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        ArrayList<Evento> imieieventi = HomeOrganizzatoreController.getInstance().getMieiEventi();
        if(imieieventi.isEmpty()){
            System.out.println("Non hai ancora aggiunto un evento");
        }
        printEventi(imieieventi);
        State homeState = new HomeOrganizzatoreState(organizzatore);
        stateMachine.goNext(homeState);
    }

    private void printEventi(ArrayList<Evento> eventiFiltrati) {
        int i = 1;
        for (Evento evento : eventiFiltrati) {
            Printer.print(i++ + ") " + evento.toString());
            Printer.println("");
        }
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------I TUOI EVENTI--------------");
    }
}
