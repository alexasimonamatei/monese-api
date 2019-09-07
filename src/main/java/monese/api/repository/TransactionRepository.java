package monese.api.repository;

import java.util.List;
import monese.api.model.Account;
import monese.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByFromAccountOrToAccountOrderByInstantDesc(Account fromAccount, Account toAccount);
}
