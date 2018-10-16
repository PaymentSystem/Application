package com.epam.lab.paymentsystem.entities;

public class Card {

    private long id;
    private long account_id;
    private long user_id;
    private boolean isActive;

    public Card() {

    }

    public Card(long id, long account_id, long user_id, boolean isActive) {
        this.id = id;
        this.account_id = account_id;
        this.user_id = user_id;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
