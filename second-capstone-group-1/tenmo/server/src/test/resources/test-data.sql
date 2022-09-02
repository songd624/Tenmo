BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfers;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
   user_id int NOT NULL DEFAULT nextval('seq_user_id'),
   username varchar(50) NOT NULL,
   password_hash varchar(200) NOT NULL,
   CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
   CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

--CREATE TABLE account (
   --account_id int NOT NULL DEFAULT nextval('seq_account_id'),
   --user_id int NOT NULL,
   --balance decimal(13, 2) NOT NULL,
   --CONSTRAINT PK_account PRIMARY KEY (account_id),
   --CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
--);
CREATE TABLE account (
   account_id int NOT NULL DEFAULT nextval('seq_account_id'),
   username varchar(50) NOT NULL,
   balance decimal(13, 2) NOT NULL,
   CONSTRAINT PK_account PRIMARY KEY (account_id),
   CONSTRAINT FK_account_tenmo_user FOREIGN KEY (username) REFERENCES tenmo_user (username),
   CONSTRAINT UQ_account_username UNIQUE (username)
);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;
CREATE TABLE transfers (
   transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
   username varchar(50) NOT NULL,
   transfer_amount decimal(13,2) NOT NULL,
   recipient_username varchar(50) NOT NULL,
   datetm timestamp DEFAULT CURRENT_TIMESTAMP,
   status varchar(10) NOT NULL,
   CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
   CONSTRAINT FK_account_tenmo_user FOREIGN KEY (username) REFERENCES tenmo_user (username)
);


INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy'),
       ('Terrance',''),
       ('Daniel',''),
       ('Mike','');

INSERT INTO account (username, balance)
VALUES('bob', 1000.00),
      ('user', 500.00),
      ('Terrance', 850.00),
      ('Daniel', 1350.00),
      ('Mike', 1300.00);


INSERT INTO transfers ( username, transfer_amount, recipient_username, status)
VALUES ('Terrance', 100.00, 'bob', 'reject'),
       ('user', 150.00, 'Terrance', 'approve'),
       ('user', 350.00, 'Daniel', 'approve'),
       ('Terrance', 300.00, 'Mike', 'approve');


COMMIT;