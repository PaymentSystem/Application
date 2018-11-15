package com.epam.lab.paymentsystem.utility;

import com.epam.lab.paymentsystem.entities.Operation;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for converter from LocalDateTime to String.
 */
public class DateConverter {

  /**
   * Date converter.
   *
   * @param operations list operations
   * @return operation list.
   */
  public static List<Operation> dateConverter(List<Operation> operations) {
    List<Operation> history = operations;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
    List<String> dateString = history.stream()
        .map((s) -> s.getDate().format(formatter))
        .collect(Collectors.toList());
    for (int i = 0; i < history.size(); i++) {
      history.get(i).setDateString(dateString.get(i));
    }
    return history;
  }
}
