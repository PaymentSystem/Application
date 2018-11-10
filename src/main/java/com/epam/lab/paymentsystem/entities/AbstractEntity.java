package com.epam.lab.paymentsystem.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  long id;

  public long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
