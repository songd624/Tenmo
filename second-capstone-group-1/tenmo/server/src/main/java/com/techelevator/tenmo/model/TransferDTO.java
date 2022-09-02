package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {


    private String recipient;

    private BigDecimal amount;

    private String status;

    private String senderName;

    public String getSenderName() {
        return senderName;
    }

    public void setSender(String sender) {
        this.senderName = sender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
