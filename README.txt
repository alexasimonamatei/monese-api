1. Clone project from https://github.com/alexasimonamatei/monese-api.git
2. Run 'mvn spring-boot:run' to launch the application
3. Make requests as follows:
 - GET all accounts from /accounts
 - GET an account statement by account id from /account/{id}/statement
 - POST a new transaction to /transactions with a JSON like {"fromAccount": 1, "toAccount": 2, "amount": 9.99}

You can also run the integration test with 'mvn test'