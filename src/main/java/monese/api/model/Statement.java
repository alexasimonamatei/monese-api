package monese.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.List;

public class Statement {

  private final long accountId;
  private final String ownerName;
  private final BigDecimal currentBalance;
  private final String currency;
  private final List<StatementRow> statementHistory;

  @JsonCreator
  public Statement(long accountId, String ownerName, BigDecimal currentBalance, String currency,
                   List<StatementRow> statementHistory) {
    this.accountId = accountId;
    this.ownerName = ownerName;
    this.currentBalance = currentBalance;
    this.currency = currency;
    this.statementHistory = statementHistory;
  }

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
