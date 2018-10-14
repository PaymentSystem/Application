package com.epam.lab.paymentsystem;


import com.epam.lab.paymentsystem.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PaymentSystemApplication {

    Controller controller;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("properties.xml");

    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
