package com.example.ispw2.model;

import java.util.ArrayList;

public class FiltroCategoria {

    private ArrayList<String> categories;

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void addCategories(String categoria) {
        this.categories.add(categoria);
    }
}
