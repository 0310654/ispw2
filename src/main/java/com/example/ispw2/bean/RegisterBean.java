package com.example.ispw2.bean;

public class RegisterBean{
    private String name;
    private String surname;
    private String email;
    private String password;

    public RegisterBean(String name, String surname, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }


    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}
    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}


}
