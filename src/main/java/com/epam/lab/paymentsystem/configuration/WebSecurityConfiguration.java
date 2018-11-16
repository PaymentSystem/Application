package com.epam.lab.paymentsystem.configuration;

import com.epam.lab.paymentsystem.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.lab.paymentsystem")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;

  public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
    auth.authenticationProvider(authProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requiresChannel()
        .anyRequest()
        .requiresSecure()
        .and()
        .authorizeRequests()
        .antMatchers("/webjars/bootstrap/4.1.3/css/**",
            "/webjars/bootstrap/4.1.3/js/**",
            "/webjars/jquery/3.3.1-1/**",
            "/registration", "/addUser", "**/addCard")
        .permitAll()
        .antMatchers("/login")
        .anonymous()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .failureUrl("/registration")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .and()
        .csrf()
        .disable();
  }
}
