package monese.api.service;

import java.util.List;
import monese.api.model.Account;
import monese.api.model.Transaction;
import monese.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionService {

  private final TransactionRepository transactionRepository;

  @Autowired
  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public Transaction save(Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public List<Transaction> getTransactionsForAccount(Account account) {
    return transactionRepository.findByFromAccountOrToAccountOrderByInstantDesc(account, account);
  }


}
