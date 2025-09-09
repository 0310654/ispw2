package com.example.ispw2.altro.Wrapper;

import com.example.ispw2.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClientiWrapper {
    private List<Cliente> clienti;

    public ClientiWrapper() {
        this.clienti = new ArrayList<>();
    }

    public List<Cliente> getClienti() {
        return clienti;
    }

    public void setClienti(List<Cliente> clienti) {
        this.clienti = clienti;
    }
    public void addCliente(Cliente cliente) {
        this.clienti.add(cliente);
    }
}
