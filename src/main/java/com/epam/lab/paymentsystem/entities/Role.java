package com.epam.lab.paymentsystem.entities;

import com.epam.lab.paymentsystem.entities.enums.Roles;

public class Role {

    private long id;
    private Roles roles;

    public Role() {
    }

    public Role(long id, Roles role) {
        this.id = id;
        this.roles = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Roles getRole() {
        return roles;
    }

    public void setRole(Roles role) {
        this.roles = role;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + roles.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Role)) {
            return false;
        }

        Role role = (Role) obj;

        return role.roles == roles;
    }
}
