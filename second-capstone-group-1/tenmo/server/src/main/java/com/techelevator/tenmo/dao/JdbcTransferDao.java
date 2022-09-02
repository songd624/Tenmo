package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> getTransfers(String username) {
        List<Transfer> transfers = new ArrayList<>();

        String sql = "SELECT transfer_id, username, transfer_amount, " +
                "recipient_username, datetm, status " +
                "FROM transfers " +
                "WHERE username = ? OR recipient_username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, username);
        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        /*
        if (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }

         */

        return transfers;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, username, transfer_amount, " +
                "recipient_username, datetm, status " +
                "FROM transfers " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }




    @Override
    public Transfer createTransfer(Transfer newTransfer) {
        String sql = "INSERT INTO transfers (username, transfer_amount, " +
                "recipient_username, datetm, status) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING transfer_id;";
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getAccountUsername(),
                newTransfer.getTransferAmount(), newTransfer.getRecipient(),
               LocalDateTime.now(), newTransfer.getStatus());

        return getTransferById(transferId);
    }

    @Override
    public List<Transfer> getPendingByUsername(String user) {
        List<Transfer> allPending = new ArrayList<>();
        String sql = "SELECT transfer_id, username, transfer_amount, recipient_username, datetm, status " +
                "FROM transfers " +
                "WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);
        while (results.next()) {
            allPending.add(mapRowToTransfer(results));
        }
        return allPending;
    }

    @Override
    public void changeStatus(String status, int transferId) {
        String sql = "UPDATE transfers " +
                "SET status = ? " +
                "WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, status, transferId);
    }

    @Override
    public void updateAfterRejected(int transferId) {
        String sql = "UPDATE transfers " +
                "SET status = 'Rejected' " +
                "WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, transferId);
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setAccountUsername(rowSet.getString("username"));
        transfer.setTransferAmount(rowSet.getBigDecimal("transfer_amount"));
        transfer.setRecipient(rowSet.getString("recipient_username"));
        transfer.setDateTime(LocalDateTime.now());
        transfer.setStatus(rowSet.getString("status"));
        return transfer;
    }
}