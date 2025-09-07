package com.example.ispw2.view.cli;

public abstract class State {

    protected State(){}

    //di base se non sono necessarie azioni all'entrata di uno stato passo subito all'esecuzione della logica interna
    public void entry(StateMachine stateMachine){
        showHeadline();
        this.execute(stateMachine);
    }

    public abstract void execute(StateMachine stateMachine);
    public void showMenu() {}
    public void showHeadline() {}
}
