package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


@Service
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;
    private TransferDao transferDao;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

      public void createAccount(String username) {
        String sql = "INSERT INTO account " +
                "(username, balance) " +
                "VALUES (?, 1000);";

            jdbcTemplate.update(sql, username);
    }

    @Override
    public Account getAccount(int userId) {
        Account account = null;
        String sql = "SELECT * " +
                "FROM account " +
                "WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()) {
            account = mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public Account getAccount(String username) {
        Account account = null;
        String sql = "SELECT account_id, username, balance " +
                "FROM account " +
                "WHERE username = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
        if (result.next()) {
            account = mapRowToAccount(result);
        }
        return account;
    }


    @Override
    public BigDecimal getBalance(String username) {

        BigDecimal output = new BigDecimal("0.00");


           String sql = "SELECT balance " +
                    "FROM account " +
                    "WHERE username = ?;";


            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

            if (results.next()) {
                output = Objects.requireNonNull(results.getBigDecimal("balance"));
                output = output.setScale(2, RoundingMode.HALF_UP);
            } else {
                System.out.println("No balance with that username");
            }

        return output;

    }


    @Override
    public void updateAfterApproval(int transferId) {

            //if there is an account
           String sql = "BEGIN TRANSACTION; " +

        "UPDATE account as a " +
        "SET balance = a.balance + transfer_amount " +
        "FROM transfers t " +
        "WHERE t.recipient_username = a.username AND transfer_id = ?; " +

        "UPDATE account as a " +
        "SET balance = a.balance - transfer_amount " +
        "FROM transfers t " +
        "WHERE t.username = a.username AND transfer_id = ?; " +

                   "UPDATE transfers " +
                   "SET status = 'Approved' " +
                   "WHERE transfer_id = ?;" +
        "COMMIT;";


        try {
            jdbcTemplate.update(sql, transferId, transferId, transferId);
        } catch (DataAccessException e) {
            System.err.println("Unable to add to account");
        }

    }

    @Override
    public BigDecimal add(String username, BigDecimal amountToAdd) {

        //if there is an account
        String sql = "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE username = ? " +
                "RETURNING balance;";

        BigDecimal output = BigDecimal.ZERO;
        try {
            output = jdbcTemplate.queryForObject(sql, BigDecimal.class, amountToAdd, username);
        } catch (DataAccessException e) {
            System.err.println("Unable to add to account");
        }

        return output;
    }

    @Override
    public BigDecimal subtract(String username, BigDecimal amountToSubtract) {
        String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE username = ? " +
                "RETURNING balance;";

        BigDecimal output = BigDecimal.ZERO;
        try {
            output = jdbcTemplate.queryForObject(sql, BigDecimal.class, amountToSubtract, username);
        } catch (DataAccessException e) {
            System.err.println("Unable to subtract to account");
        }

        return output;
    }


    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setAccountId(rowSet.getInt("account_id"));
        account.setUsername(rowSet.getString("username"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }



}
