package com.epam.lab.paymentsystem.utility;

import com.epam.lab.paymentsystem.entities.Operation;
import java.time.format.DateTimeFormatter;
import org.springframework.data.domain.Page;

/**
 * Class for converter from LocalDateTime to String.
 */
public class DateConverter {

  /**
   * Date converter.
   *
   * @param operations page operations
   * @return operation page.
   */
  public static Page<Operation> dateConverter(Page<Operation> operations) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
    operations.forEach((s) -> s.setDateString(s.getDate().format(formatter)));
    return operations;
  }
}
