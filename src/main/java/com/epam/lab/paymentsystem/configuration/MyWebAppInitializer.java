package com.epam.lab.paymentsystem.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext container) {
    // Create the dispatcher servlet's Spring application context
    AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
    dispatcherContext.register(ServletConfiguration.class);

    // Register and map the dispatcher servlet
    ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
  }
}
