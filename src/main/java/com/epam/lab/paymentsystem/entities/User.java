package com.epam.lab.paymentsystem.entities;

public class User {

    private long id;
    private String name;
    private String password;
    private String login;
    private int roleId;

    public User() {

    }

    public User(long id, String name, String password, String login, int roleId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.login = login;
        this.roleId = roleId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;

        return user.login.equals(login);
    }
}
