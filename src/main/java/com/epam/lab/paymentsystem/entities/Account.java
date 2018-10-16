package com.epam.lab.paymentsystem.entities;

public class Account {

    private long id;
    private long user_id;
    private double amount;
    private boolean isActive;

    public Account() {

    }

    public Account(long id, long user_id, double amount, boolean isActive) {
        this.id = id;
        this.user_id = user_id;
        this.amount = amount;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Long.valueOf(user_id).hashCode();
        result = 31 * result + Double.valueOf(amount).hashCode();
        result = 31 * result + Boolean.valueOf(isActive).hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }

        Account account = (Account) obj;

        return account.user_id == user_id &&
                account.amount == amount &&
                account.isActive == isActive;
    }
}
