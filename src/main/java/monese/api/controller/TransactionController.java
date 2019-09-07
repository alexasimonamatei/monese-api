package monese.api.controller;

import java.math.BigDecimal;
import monese.api.exception.BadRequestException;
import monese.api.model.Account;
import monese.api.model.Transaction;
import monese.api.model.TransactionRequest;
import monese.api.repository.AccountRepository;
import monese.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  private final TransactionService transactionService;
  private final AccountRepository accountRepository;

  @Autowired
  public TransactionController(TransactionService transactionService, AccountRepository accountRepository) {
    this.transactionService = transactionService;
    this.accountRepository = accountRepository;
  }

  @PostMapping("/transactions")
  @Transactional
  public Transaction createTransaction(@RequestBody TransactionRequest transactionRequest) throws BadRequestException {
    final Account fromAccount = accountRepository.findById(transactionRequest.getFromAccount())
            .orElseThrow(() -> new BadRequestException
                    ("Account {" + transactionRequest.getFromAccount() + "} not found"));
    final Account toAccount = accountRepository.findById(transactionRequest.getToAccount())
            .orElseThrow(() -> new BadRequestException
                    ("Account {" + transactionRequest.getToAccount() + "} not found"));
    if (fromAccount.equals(toAccount)) {
      throw new BadRequestException("Cannot transfer money to/from the same account {" + fromAccount + "}");
    }
    if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) < 0) {
      throw new BadRequestException("Amount {" + transactionRequest.getAmount() + "} must be a positive number");
    }
    if (transactionRequest.getAmount().compareTo(fromAccount.getBalance()) > 0) {
      throw new BadRequestException("Insufficient funds available in account {" + fromAccount + "}");
    }
    updateAccountBalances(fromAccount, transactionRequest.getAmount().negate());
    updateAccountBalances(toAccount, transactionRequest.getAmount());
    return transactionService.save(new Transaction(fromAccount, toAccount, transactionRequest.getAmount()));
  }

  private void updateAccountBalances(Account account, BigDecimal transactedAmount) {
    account.setBalance(account.getBalance().add(transactedAmount));
    accountRepository.save(account);
  }
}
