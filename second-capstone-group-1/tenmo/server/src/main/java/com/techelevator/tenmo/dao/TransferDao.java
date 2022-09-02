package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getTransfers(String user);

    Transfer getTransferById(int transferId);

    Transfer createTransfer(Transfer transfer);

    List<Transfer> getPendingByUsername(String user);

    void changeStatus(String status, int transferId);

    void updateAfterRejected(int transferId);
}
