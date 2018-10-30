package com.epam.lab.paymentsystem.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Implementations of this SPI detected automatically by SpringServletContainerInitializer,
 * which itself is bootstrapped automatically by any Servlet 3.0 container, it allows configure the
 * ServletContext programmatically -- as opposed to (or possibly in conjunction with)
 * the traditional web.xml-based approach.
 *
 * @author unascribed
 * @since 0.0.1
 */
public class MyWebAppInitializer implements WebApplicationInitializer {

  /**
   * This method creates the 'root' Spring application context and manage the lifecycle
   * of the root application context via ContextLoaderListener, then creates the dispatcher
   * servlet's Spring application context, finally register and map the dispatcher servlet.
   *
   * @param container Servlet Context
   * @link org.springframework.web.context.support.AnnotationConfigWebApplicationContext
   * @link org.springframework.web.context.ContextLoaderListener
   * @link javax.servlet
   */
  @Override
  public void onStartup(ServletContext container) {
    AnnotationConfigWebApplicationContext rootContext =
        new AnnotationConfigWebApplicationContext();
    rootContext.register(ApplicationConfiguration.class);

    container.addListener(new ContextLoaderListener(rootContext));

    AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
    dispatcherContext.register(DispatcherConfiguration.class);

    ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
  }
}
