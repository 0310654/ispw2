package com.example.ispw2.model;

public abstract class User {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String type;

    //utente non registrato
    protected User(String email, String name, String surname){
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

//utente registrato
    protected User(String email, String name, String surname, String password, String type){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.type = type;
    }

    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}

    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}

    public String getType() {return this.type;}

    public abstract String toString();

}
