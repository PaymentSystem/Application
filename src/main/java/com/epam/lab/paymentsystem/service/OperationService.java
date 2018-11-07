package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Operation;
import com.epam.lab.paymentsystem.entities.User;
import java.util.List;

public interface OperationService {
  void makePayment(Operation operation);

  void writeHistory(Operation operation);

  List<Operation> historyOperation(long cardId);
}
