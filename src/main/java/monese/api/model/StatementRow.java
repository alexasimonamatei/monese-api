package monese.api.model;

import java.math.BigDecimal;
import java.time.Instant;

public class StatementRow {

  private final long otherAccountId;
  private final String otherAccountName;
  private final BigDecimal amount;
  private final Instant instant;

  public StatementRow(long otherAccountId, String otherAccountName, BigDecimal amount, Instant instant) {
    this.otherAccountId = otherAccountId;
    this.otherAccountName = otherAccountName;
    this.amount = amount;
    this.instant = instant;
  }

  public long getOtherAccountId() {
    return otherAccountId;
  }

  public String getOtherAccountName() {
    return otherAccountName;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Instant getInstant() {
    return instant;
  }

  public static StatementRow fromTransaction(Transaction transaction, Account account) {
    final BigDecimal amount = transaction.getFromAccount().equals(account)
            ? transaction.getAmount() : transaction.getAmount().negate();
    return new StatementRow(account.getId(), account.getOwnerName(), amount, transaction.getInstant());
  }
}
