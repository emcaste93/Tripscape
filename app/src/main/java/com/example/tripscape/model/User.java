package com.example.tripscape.model;

public class User {
    private String name;
    private String email;
    private long login;

    public User () {}

    public User (String name, String email, long login) {
        this.name = name;
        this.email = email;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getLogin() {
        return login;
    }

    public void setLogin(long login) {
        this.login = login;
    }

    public User (String name, String email) {
        this(name, email, System.currentTimeMillis());
    }
}
