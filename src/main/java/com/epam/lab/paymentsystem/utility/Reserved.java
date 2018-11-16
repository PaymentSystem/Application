package com.epam.lab.paymentsystem.utility;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Reserved {

  private List<String> reserved;

  {
    reserved = new ArrayList<>();
    reserved.add("login");
    reserved.add("registration");
    reserved.add("addUser");
    reserved.add("my");
  }

  public List<String> getReserved() {
    return reserved;
  }
}
