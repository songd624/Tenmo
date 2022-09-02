package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transfer {

    private int transferId;
    private String accountUsername;
    private BigDecimal transferAmount;
    private String recipient;
    private LocalDateTime dateTime;
    private String status;

    public Transfer(int transferId, String accountUsername, BigDecimal transferAmount,
                    String recipient, LocalDateTime dateTime, String status) {
        this.transferId = transferId;
        this.accountUsername = accountUsername;
        this.transferAmount = transferAmount;
        this.recipient = recipient;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Transfer() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }


    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }


    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transfer ID: " + this.transferId + "\n" +
                "Account Username: " + this.accountUsername + "\n" +
                "Transfer Amount: " + this.transferAmount + "\n" +
                "Recipient Username: " + this.recipient + "\n" +
                "Timestamp: " + this.dateTime + "\n" +
                "Status: " + this.status;
    }
}
