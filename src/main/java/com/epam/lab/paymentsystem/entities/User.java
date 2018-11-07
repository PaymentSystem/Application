package com.epam.lab.paymentsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO class object, class which is a role database table representation. This class implements
 * mapping fields with the corresponding cells of the database table columns.
 */
@Entity
@Table(name = "users")
public class User extends AbstractEntity {
  @Column(name = "name")
  private String name;

  @Column(name = "password")
  private String password;

  @Column(name = "login")
  private String login;

  @ManyToOne
  @JoinColumn(name = "id_role")
  private Role role;

  public User() {
  }

  /**
   * Instantiates a new User.
   *
   * @param name     the name
   * @param password the password
   * @param login    the login
   * @param role     the role
   */
  public User(String name, String password, String login, Role role) {
    this.name = name;
    this.password = password;
    this.login = login;
    this.role = role;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
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
