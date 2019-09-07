1. Clone project from https://github.com/alexasimonamatei/monese-api.git
2. Run 'mvn spring-boot:run' to launch the application
3. Database:
 - Data is saved in an H2 in-memory database so all changes are lost when the application is restarted. To persist data
in a file, comment line 1 in application.properties and uncomment line 2.
 - Database already contains two accounts with ids 1 and 2 and positive balances (see data.sql for full values)
4. Make HTTP requests as follows:
 - GET all accounts from /accounts
 - GET an account statement by account id from /account/{id}/statement
 - POST a new transaction to /transactions with a JSON like {"fromAccount": 1, "toAccount": 2, "amount": 9.99}

You can also run the integration test with 'mvn test'
