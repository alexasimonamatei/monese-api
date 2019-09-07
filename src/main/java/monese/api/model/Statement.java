package monese.api.model;

import java.math.BigDecimal;
import java.util.List;

public class Statement {

  private final long accountId;
  private final String ownerName;
  private final BigDecimal currentBalance;
  private final String currency;
  private final List<StatementRow> statementHistory;

  public Statement(Account account, List<StatementRow> statementHistory) {
    this.accountId = account.getId();
    this.ownerName = account.getOwnerName();
    this.currentBalance = account.getBalance();
    this.currency = account.getCurrency();
    this.statementHistory = statementHistory;
  }

  public long getAccountId() {
    return accountId;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public BigDecimal getCurrentBalance() {
    return currentBalance;
  }

  public String getCurrency() {
    return currency;
  }

  public List<StatementRow> getStatementHistory() {
    return statementHistory;
  }
}
