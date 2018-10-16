package com.epam.lab.paymentsystem.entities;

import java.time.LocalDateTime;

public class Operation {

    private long id;
    private long source_id;
    private long target_id;
    private double amount;
    private LocalDateTime date;

    public Operation() {

    }

    public Operation(long id, long source_id, long target_id, double amount, LocalDateTime date) {
        this.id = id;
        this.source_id = source_id;
        this.target_id = target_id;
        this.amount = amount;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSource_id() {
        return source_id;
    }

    public void setSource_id(long source_id) {
        this.source_id = source_id;
    }

    public long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(long target_id) {
        this.target_id = target_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Long.valueOf(source_id).hashCode();
        result = 31 * result + Long.valueOf(target_id).hashCode();
        result = 31 * result + Double.valueOf(amount).hashCode();
        result = 31 * result + date.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Operation)) {
            return false;
        }

        Operation operation = (Operation) obj;

        return operation.source_id == source_id &&
                operation.target_id == target_id &&
                operation.amount == amount &&
                operation.date.equals(date);
    }
}
