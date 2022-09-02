package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{

    private JdbcAccountDao accountSut;
    private JdbcUserDao userSut;

    @Before
    public void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        accountSut = new JdbcAccountDao(jdbcTemplate);
        userSut = new JdbcUserDao(jdbcTemplate);

    }

    /*  @Test
      public void createAccount(){
          // Arrange
          accountSut.createAccount("ACCOUNT_TEST_USER");
          BigDecimal expectedBalance = new BigDecimal("1000.00");
          // Action
          accountSut.createAccount("ACCOUNT_TEST_USER");
          // Assertion
          Assert.assertEquals(expectedBalance, accountSut.getBalance("ACCOUNT_TEST_USER"));
      }
  */
    @Test
    public void getAccountBalanceByUsername(){
        // Arrange
        //accountSut.createAccount("Terrance");
        BigDecimal expectedBalance = new BigDecimal("850.00");
        // Action
        accountSut.getAccount("Terrance");
        // Assertion
        Assert.assertEquals(expectedBalance, accountSut.getBalance("Terrance"));
    }
}