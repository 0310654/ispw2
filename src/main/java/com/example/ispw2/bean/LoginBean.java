package com.example.ispw2.bean;

public class LoginBean {

    private String email;
    private String password;
    private String type;

    public LoginBean(String email, String password) {
        this.email = email;
        this.password = password;
        this.type = null;
    }

    public LoginBean(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.type = userType;
    }

    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}
    public String getType() {return this.type;}
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setType(String type) {
        this.type = type;
    }
}
