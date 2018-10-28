package com.epam.lab.paymentsystem.entities;

public class Account {

    private long id;
    private long userId;
    private double amount;
    private boolean isActive;

    public Account() {

    }

    /**
     * Constructor for account.
     *
     * @param id       id of account
     * @param userId   id of user
     * @param amount   amount of account
     * @param isActive boolean flag
     */
    public Account(long id, long userId, double amount, boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        result = 31 * result + Long.valueOf(userId).hashCode();
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

        return account.userId == userId
                && account.amount == amount
                && account.isActive == isActive;
    }
}
