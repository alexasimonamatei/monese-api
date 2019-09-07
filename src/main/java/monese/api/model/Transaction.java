package monese.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "fromAccount")
  private Account fromAccount;

  @ManyToOne
  @JoinColumn(name = "toAccount")
  private Account toAccount;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private Instant instant = Instant.now();

  @JsonCreator
  public Transaction() {}

  public Transaction(Account fromAccount, Account toAccount, BigDecimal amount) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Account getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(Account fromAccount) {
    this.fromAccount = fromAccount;
  }

  public Account getToAccount() {
    return toAccount;
  }

  public void setToAccount(Account toAccount) {
    this.toAccount = toAccount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Instant getInstant() {
    return instant;
  }

  public void setInstant(Instant instant) {
    this.instant = instant;
  }
}
