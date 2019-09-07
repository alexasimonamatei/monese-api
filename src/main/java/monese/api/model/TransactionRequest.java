package monese.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;

public class TransactionRequest {

  private final long fromAccount;
  private final long toAccount;
  private final BigDecimal amount;

  @JsonCreator
  public TransactionRequest(long fromAccount, long toAccount, BigDecimal amount) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }

  public long getFromAccount() {
    return fromAccount;
  }

  public long getToAccount() {
    return toAccount;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
