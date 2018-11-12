package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.configuration.DispatcherConfiguration;
import com.epam.lab.paymentsystem.configuration.H2TestConfiguration;
import com.epam.lab.paymentsystem.util.H2TestDataInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    DispatcherConfiguration.class,
    H2TestConfiguration.class
})
@WebAppConfiguration
public abstract class AbstractControllerTest {
  @Autowired
  private H2TestDataInitializer h2TestDataInitializer;

  @BeforeEach
  public void setUp() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    h2TestDataInitializer.init();
  }
}
