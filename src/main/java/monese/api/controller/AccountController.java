package monese.api.controller;

import java.util.List;
import java.util.stream.Collectors;
import monese.api.exception.BadRequestException;
import monese.api.model.Account;
import monese.api.model.Statement;
import monese.api.model.StatementRow;
import monese.api.repository.AccountRepository;
import monese.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  private final AccountRepository accountRepository;
  private final TransactionService transactionService;

  @Autowired
  public AccountController(AccountRepository accountRepository, TransactionService transactionService) {
    this.accountRepository = accountRepository;
    this.transactionService = transactionService;
  }

  @GetMapping("/accounts")
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  @GetMapping("/accounts/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId)
          throws BadRequestException {
    final Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new BadRequestException("Account with {" + accountId + "} does not exist"));
    return ResponseEntity.ok().body(account);
  }

  @GetMapping("/accounts/{id}/statement")
  public ResponseEntity<Statement> getAccountTransactions(@PathVariable(value = "id") Long accountId)
          throws BadRequestException {
    final Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new BadRequestException("Account with {" + accountId + "} does not exist"));
    final List<StatementRow> statementHistory = transactionService.getTransactionsForAccount(account).stream().map(t ->
            StatementRow.fromTransaction(t, t.getFromAccount().equals(account) ? t.getToAccount() : t.getFromAccount())
    ).collect(Collectors.toList());
    final Statement statement = new Statement(account, statementHistory);
    return ResponseEntity.ok().body(statement);
  }
}
