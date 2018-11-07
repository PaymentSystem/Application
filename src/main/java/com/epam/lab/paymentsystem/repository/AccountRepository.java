package com.epam.lab.paymentsystem.repository;

import com.epam.lab.paymentsystem.entities.Account;
import com.epam.lab.paymentsystem.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * Account specific extension of JPA.
 *
 * @author unascribed
 * @link org.springframework.data.jpa.repository
 * @link org.springframework.data.repository
 * @since 0.0.1
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
  /**
   * Returns list of accounts from database which are linked to passed user.
   *
   * @param user user entity
   * @return list of account entities
   */
  List<Account> getAllByUser(User user);

  /**
   * Returns account form database by passed id.
   *
   * @param id id of account
   * @return account entity
   */
  Account getAccountById(long id);
}
