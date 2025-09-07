package com.example.ispw2.view.cli;

public interface StateMachine {
    void start();
    void goBack();
    void goNext(State state);
}
