package monese.api;

import java.math.BigDecimal;
import monese.api.model.Statement;
import monese.api.model.Transaction;
import monese.api.model.TransactionRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionIntegrationTest {

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Sql({"/data.sql"})
  public void testSendingMoneyUpdatesBothBalances() {
    final BigDecimal transactedAmount = new BigDecimal(100);

    // Get statements prior to transaction
    final Statement fromAccountStatementBefore = getStatementForAccount(1);
    final Statement toAccountStatementBefore = getStatementForAccount(2);

    // Perform transaction - move 100 GBP from one account to another
    final TransactionRequest request = new TransactionRequest(1, 2, transactedAmount);
    restTemplate.postForObject(createUrlWithPort("/transactions"), request, Transaction.class);

    // Get statements after transaction
    final Statement fromAccountStatementAfter = getStatementForAccount(1);
    final Statement toAccountStatementAfter = getStatementForAccount(2);

    // Assert that both accounts balances have changed by the right amount
    final BigDecimal fromAccountBalanceBefore = fromAccountStatementBefore.getCurrentBalance();
    final BigDecimal fromAccountBalanceAfter = fromAccountStatementAfter.getCurrentBalance();
    assertEquals(transactedAmount, fromAccountBalanceAfter.subtract(fromAccountBalanceBefore).negate());

    final BigDecimal toAccountBalanceBefore = toAccountStatementBefore.getCurrentBalance();
    final BigDecimal toAccountBalanceAfter = toAccountStatementAfter.getCurrentBalance();
    assertEquals(transactedAmount, toAccountBalanceAfter.subtract(toAccountBalanceBefore));
  }

  private Statement getStatementForAccount(long accountId) {
    return restTemplate.getForObject(createUrlWithPort("/accounts/" + accountId + "/statement"), Statement.class);
  }

  private String createUrlWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}
