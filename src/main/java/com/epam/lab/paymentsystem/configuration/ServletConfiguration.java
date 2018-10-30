package com.epam.lab.paymentsystem.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.epam.lab.paymentsystem")
public class ServletConfiguration {

  private static final Logger LOGGER = LogManager.getLogger(ServletConfiguration.class);
  private static final String PREFIX = "/WEB-INF/jsp/";
  private static final String SUFFIX = ".jsp";

  /**
   * Bean defenition for viewResolver.
   *
   * @return ViewResolver
   */
  @Bean
  public ViewResolver internalResourceViewResolver() {
    LOGGER.info("Internal resource view resolver");
    InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setPrefix(PREFIX);
    bean.setSuffix(SUFFIX);
    return bean;
  }
}