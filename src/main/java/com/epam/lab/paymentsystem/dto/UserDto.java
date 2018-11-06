package com.epam.lab.paymentsystem.dto;

import com.epam.lab.paymentsystem.entities.Role;

public class UserDto {
  private long id;
  private String login;
  private String name;
  private String password;
  private Role role;

  public UserDto() {
  }

  /**
   * Constructor with parametrs for user dto.
   *
   * @param id       user's id
   * @param login    user's login
   * @param name     user's name
   * @param password user's password
   * @param role     user linked to this role
   */
  public UserDto(long id, String login, String name, String password, Role role) {
    this.id = id;
    this.login = login;
    this.name = name;
    this.password = password;
    this.role = role;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}