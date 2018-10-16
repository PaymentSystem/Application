package com.epam.lab.paymentsystem.entities;

public class User {

    private long id;
    private String name;
    private String password;
    private String login;
    private long role_id;

    public User(){

    }

    public User(long id, String name, String password, String login, long role_id) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.login = login;
        this.role_id = role_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }
}
