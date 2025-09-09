package com.example.ispw2.model;

public class Filtri {
    private String flt;
    private String filterType;


    public Filtri(String flt, String filterType) {
        this.flt = flt;
        this.filterType = filterType;
    }

    public String getFlt() {return this.flt;}
    public String getFilterType(){return this.filterType;}
}
