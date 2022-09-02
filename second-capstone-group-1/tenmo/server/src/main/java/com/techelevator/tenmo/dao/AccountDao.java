package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(String username);

    void updateAfterApproval(int transferId);

    BigDecimal add(String username, BigDecimal amountToAdd);

    BigDecimal subtract(String username, BigDecimal amountToSubtract);

    Account getAccount(String username);

    Account getAccount(int userId);

    void createAccount(String username);

}
