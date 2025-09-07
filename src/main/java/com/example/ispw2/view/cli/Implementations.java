package com.example.ispw2.view.cli;


import java.util.ArrayDeque;
import java.util.Deque;

public class Implementations implements StateMachine {

    /*utilizzo una deque(struttura dati che permette di inserire sia in testa che in coda)
     come pila di stati visitati sulla quale effettuare operazioni di push e pop*/
    private Deque<State> stateHistory = new ArrayDeque<>();

    private State currentState;
    //alla creazione della macchina setto il current state
    public Implementations() {
        this.currentState = new InitialState();
    }

    @Override
    public void start() {transitionTo(currentState);}

    private void transitionTo(State currentState) {
        currentState.entry(this);
    }

    //goBack e goNext settano il current state e la stateHistory prima di eseguire la transizione
    @Override
    public void goBack() {
        currentState = stateHistory.pop();
        transitionTo(currentState);
    }

    @Override
    public void goNext(State state) {
        stateHistory.push(currentState);
        currentState = state;
        transitionTo(currentState);
    }
}
