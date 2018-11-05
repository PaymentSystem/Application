package com.epam.lab.paymentsystem.service;

import com.epam.lab.paymentsystem.entities.Operation;

public interface OperationService {
  void makePayment(Operation operation);

  void writeHistory(Operation operation);
}
