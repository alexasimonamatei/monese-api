drop table accounts IF EXISTS;
drop table transactions IF EXISTS;

create TABLE accounts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    owner_name VARCHAR(50) NOT NULL,
    balance DECIMAL NOT NULL,
    currency VARCHAR(3) NOT NULL,
    PRIMARY KEY(id)
);

create TABLE transactions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    from_account BIGINT NOT NULL,
    to_account BIGINT NOT NULL,
    amount DECIMAL NOT NULL,
    instant TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (from_account) REFERENCES accounts(id),
    FOREIGN KEY (to_account) REFERENCES accounts(id)
);

insert into accounts (owner_name, balance, currency) values
  ('Bob', '5000', 'GBP'),
  ('Alice', '6000', 'GBP');