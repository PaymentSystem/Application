package com.epam.lab.paymentsystem.configuration;

import com.epam.lab.paymentsystem.entities.AbstractEntity;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.impl.UserDetailsServiceImpl;
import com.epam.lab.paymentsystem.utility.Reserved;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.lab.paymentsystem")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;
  @Autowired
  private AccountService accountService;
  @Autowired
  private Reserved reserved;
  @Autowired
  private CardService cardService;

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
            "/style/**",
            "/js/**",
            "/registration", "/addUser", "/myCards", "/history")
        .permitAll()
        .antMatchers("/{userLogin}/account/{accountId}/card/{cardId}/**")
        .access("@webSecurityConfiguration.checkCard(authentication, #cardId)"
            + " and hasRole('USER')")
        .antMatchers("/{userLogin}/account/{accountId}/**")
        .access("@webSecurityConfiguration.checkAccount(authentication, #accountId)"
            + " and hasRole('USER')")
        .antMatchers("/{userLogin}/**")
        .access("(@webSecurityConfiguration.checkUser(authentication, #userLogin) "
            + "and !hasRole('BLOCKED'))"
            + " or hasRole('ADMIN')")
        .antMatchers("/login")
        .anonymous()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/error/403")
        .and()
        .formLogin()
        .loginPage("/login")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .and()
        .csrf()
        .disable();
  }

  /**
   * Checks if account belongs to current logged user.
   *
   * @param authentication authentication
   * @param accountId      accountId
   * @return boolean
   */
  public boolean checkAccount(Authentication authentication, long accountId) {
    String login = authentication.getName();
    List<Long> list = accountService.getAllAccountsOfUser(login)
        .stream().map(AbstractEntity::getId)
        .collect(Collectors.toList());
    return list.contains(accountId);
  }

  /**
   * Checks if card belongs to current logged user.
   *
   * @param authentication authentication
   * @param cardId         cardId
   * @return boolean
   */
  public boolean checkCard(Authentication authentication, long cardId) {
    String login = authentication.getName();
    List<Long> list = cardService.getAllCardsByLogin(login)
        .stream().map(AbstractEntity::getId)
        .collect(Collectors.toList());
    return list.contains(cardId);
  }

  /**
   * Checks if user page belongs to current logged user.
   *
   * @param authentication authentication
   * @param userLogin      accountId
   * @return boolean
   */
  public boolean checkUser(Authentication authentication, String userLogin) {
    if (reserved.getReserved().contains(userLogin)) {
      return true;
    }
    String login = authentication.getName();

    return login.equals(userLogin);
  }
}
