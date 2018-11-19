package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.dto.OperationDto;
import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.exception.AccountArgumentException;
import com.epam.lab.paymentsystem.exception.CardArgumentException;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.exception.MoneyTransferException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.epam.lab.paymentsystem.controller"})
public class GlobalControllerAdvice {

  private static final String MESSAGE_EXCEPTION = "messageException";

  @Autowired
  private MessageSource messageSource;

  /**
   * Handles LoginAlreadyExistException in all controllers.
   *
   * @param exception exception
   * @return model and view
   */
  @ExceptionHandler(LoginAlreadyExistsException.class)
  public ModelAndView loginAlreadyExistsExceptionHandle(Exception exception, Locale locale) {
    ModelAndView mav = prepareModel("login", exception, locale);
    mav.addObject("userDto", new UserDto());
    return mav;
  }

  /**
   * Handles MoneyTransferException in all controllers.
   *
   * @param exception exception
   * @return model and view
   */
  @ExceptionHandler(MoneyTransferException.class)
  public ModelAndView moneyTransferExceptionHandle(Exception exception, Locale locale) {
    ModelAndView mav = prepareModel("operation", exception, locale);
    mav.addObject("operationDto", new OperationDto());
    return mav;
  }

  /**
   * Handles CardArgumentException in all controllers.
   *
   * @param exception exception
   * @return model and view
   */
  @ExceptionHandler(CardArgumentException.class)
  public ModelAndView cardArgumentExceptionHandle(Exception exception, Locale locale) {
    ModelAndView mav = prepareModel("addCard", exception, locale);
    mav.addObject("cardDto", new CardDto());
    return mav;
  }

  /**
   * Handles AccountArgumentException in all controllers.
   *
   * @param exception exception
   * @return model and view
   */
  @ExceptionHandler(AccountArgumentException.class)
  public ModelAndView accountArgumentExceptionHandle(Exception exception,
                                                     HttpServletRequest req,
                                                     Locale locale) {
    ModelAndView mav;
    if (req.getRequestURI().endsWith("/addAmount")) {
      mav = prepareModel("addAmount", exception, locale);
    } else {
      mav = prepareModel("addAccount", exception, locale);
    }
    mav.addObject("accountDto", new AccountDto());
    return mav;
  }

  /**
   * Initialize ModelAndView object to sending response.
   *
   * @param viewName displayed view
   * @param e        handled exception
   * @return model and view
   */
  private ModelAndView prepareModel(String viewName, Exception e, Locale locale) {
    ModelAndView mav = new ModelAndView();
    String ex = messageSource.getMessage(e.getMessage(), new Object[]{}, locale);
    mav.addObject(MESSAGE_EXCEPTION, ex);
    mav.setViewName(viewName);
    return mav;
  }
}