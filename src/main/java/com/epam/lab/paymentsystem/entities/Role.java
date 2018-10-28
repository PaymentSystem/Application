package com.epam.lab.paymentsystem.entities;

import com.epam.lab.paymentsystem.entities.enums.Roles;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_role")
    private int id;
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private Roles roleStatus;

    public Role() {
    }

    public Role(int id, Roles roleStatus) {
        this.id = id;
        this.roleStatus = roleStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Roles getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Roles roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + roleStatus.hashCode();
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

        return role.roleStatus == roleStatus;
    }
}
