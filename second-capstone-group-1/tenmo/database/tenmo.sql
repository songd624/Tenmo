BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfers CASCADE;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

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
	datetm timestamp,
	status varchar(10) NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (username) REFERENCES tenmo_user (username)
);

COMMIT;

--ROLLBACK;
