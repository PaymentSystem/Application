package com.epam.lab.paymentsystem.controller;

import com.epam.lab.paymentsystem.dto.AccountDto;
import com.epam.lab.paymentsystem.dto.CardDto;
import com.epam.lab.paymentsystem.dto.OperationDto;
import com.epam.lab.paymentsystem.dto.UserDto;
import com.epam.lab.paymentsystem.exception.AccountArgumentException;
import com.epam.lab.paymentsystem.exception.CardArgumentException;
import com.epam.lab.paymentsystem.exception.LoginAlreadyExistsException;
import com.epam.lab.paymentsystem.exception.MoneyTransferException;
import com.epam.lab.paymentsystem.exception.ResourceNotFoundException;
import com.epam.lab.paymentsystem.service.AccountService;
import com.epam.lab.paymentsystem.service.CardService;
import com.epam.lab.paymentsystem.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.epam.lab.paymentsystem.controller"})
public class GlobalControllerAdvice {

  private static final String MESSAGE_EXCEPTION = "messageException";
  private static final String USER_LOGIN_PATH_VARIABLE = "userLogin";
  private static final String ACCOUNT_ID_PATH_VARIABLE = "accountId";
  private static final String CARD_ID_PATH_VARIABLE = "cardId";
  @Autowired
  private UserService userService;
  @Autowired
  private AccountService accountService;
  @Autowired
  private CardService cardService;

  /**
   * Throws Http Response NOT_FOUND.
   *
   * @param pathVariables map of path variables
   */
  @ModelAttribute
  public void advise(@PathVariable Map<String, String> pathVariables) {
    if (pathVariables.containsKey(USER_LOGIN_PATH_VARIABLE)) {
      String userLogin = pathVariables.get(USER_LOGIN_PATH_VARIABLE);
      if (userService.getUserByLogin(userLogin) == null) {
        throw new ResourceNotFoundException();
      }
    }
    if (pathVariables.containsKey(ACCOUNT_ID_PATH_VARIABLE)) {
      long accountId = Long.valueOf(pathVariables.get(ACCOUNT_ID_PATH_VARIABLE));
      if (accountService.getAccountById(accountId) == null) {
        throw new ResourceNotFoundException();
      }
    }
    if (pathVariables.containsKey(CARD_ID_PATH_VARIABLE)) {
      long cardId = Long.valueOf(pathVariables.get(CARD_ID_PATH_VARIABLE));
      if (cardService.getCardById(cardId) == null) {
        throw new ResourceNotFoundException();
      }
    }
  }

  /**
   * Handles LoginAlreadyExistException in all controllers.
   *
   * @param exception exception
   * @return model and view
   */
  @ExceptionHandler(LoginAlreadyExistsException.class)
  public ModelAndView loginAlreadyExistsExceptionHandle(Exception exception) {
    ModelAndView mav = prepareModel("registration", exception);
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
  public ModelAndView moneyTransferExceptionHandle(Exception exception) {
    ModelAndView mav = prepareModel("operation", exception);
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
  public ModelAndView cardArgumentExceptionHandle(Exception exception) {
    ModelAndView mav = prepareModel("addCard", exception);
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
  public ModelAndView accountArgumentExceptionHandle(Exception exception) {
    ModelAndView mav = prepareModel("addAccount", exception);
    mav.addObject("accountDto", new AccountDto());
    return mav;
  }

  /**
   * Initialize ModelAndView object to sending response.
   *
   * @param viewName displayed view
   * @param e handled exception
   * @return model and view
   */
  private ModelAndView prepareModel(String viewName, Exception e) {
    ModelAndView mav = new ModelAndView();
    mav.addObject(MESSAGE_EXCEPTION, e.getMessage());
    mav.setViewName(viewName);
    return mav;
  }
}