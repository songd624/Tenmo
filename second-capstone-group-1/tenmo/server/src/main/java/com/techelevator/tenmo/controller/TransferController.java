package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    public TransferController() {
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
       return transferDao.getTransferById(id);
    }



    //@ResponseStatus(HttpStatus.CREATED)
    @RequestMapping( path = "/transfers/request", method = RequestMethod.POST)
    public Transfer transferMoney(@RequestBody TransferDTO transferDTO, Principal principal) {

        String recipient = principal.getName();
        String recipientName = userDao.findByUsername(recipient).getUsername();
        String senderName = transferDTO.getSenderName();

        Account senderAccount = accountDao.getAccount(senderName);
        Account recipientAccount = accountDao.getAccount(recipientName);

        BigDecimal amountToSend = transferDTO.getAmount();

        if(amountToSend.compareTo(new BigDecimal("0")) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot request $0 or negative money.");
        } else if (senderName.equals(recipientName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot request money from yourself.");
        }

        Transfer transfer = new Transfer();
        transfer.setTransferAmount(transferDTO.getAmount());
        transfer.setRecipient(recipientName);
        transfer.setAccountUsername(senderName);
        transfer.setDateTime(LocalDateTime.now());
        transfer.setStatus("Pending");
        return transferDao.createTransfer(transfer);
    }


    @RequestMapping( path = "/transfers/send", method = RequestMethod.POST)
    public Transfer sendMoney(@RequestBody TransferDTO transferDTO, Principal principal) {


        String sender = principal.getName();
        String senderName = userDao.findByUsername(sender).getUsername();
        User receiver = userDao.findByUsername(transferDTO.getRecipient());
        String recipientName = receiver.getUsername();

        Account senderAccount = accountDao.getAccount(senderName);
        Account recipientAccount = accountDao.getAccount(recipientName);

        BigDecimal amountToSend = transferDTO.getAmount();


        if(amountToSend.compareTo(new BigDecimal("0")) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot send $0 or negative money.");
        } else if (senderName.equals(recipientName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot send money to yourself.");
        } else if (amountToSend.compareTo(senderAccount.getBalance()) >= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have enough funds.");
        } else {

            accountDao.subtract(senderName, amountToSend);
            accountDao.add(recipientName, amountToSend);

            senderAccount.setBalance(senderAccount.getBalance().subtract(amountToSend));
            recipientAccount.setBalance((recipientAccount.getBalance().add(amountToSend)));

        }
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(transferDTO.getAmount());
        transfer.setRecipient(transferDTO.getRecipient());
        transfer.setAccountUsername(principal.getName());
        transfer.setDateTime(LocalDateTime.now());
        transfer.setStatus("Approved");
        return transferDao.createTransfer(transfer);
    }


    @RequestMapping(path = "/transfers/pending", method = RequestMethod.GET)
    public List<Transfer> getAllPending(Principal principal) {
        return transferDao.getPendingByUsername(principal.getName());
    }


    @RequestMapping(path = "/transfers/pending/{id}", method = RequestMethod.PUT)
    public void changeStatusOfTransfer(@PathVariable int id, @Valid @RequestBody TransferDTO transferDTO) {

        if (transferDTO.getStatus().equals("Rejected")) {
            transferDao.updateAfterRejected(id);
        }
        if(transferDTO.getStatus().equals("Approved")) {
            accountDao.updateAfterApproval(id);
        }
    }


    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfersByUsername(Principal principal) {
        return transferDao.getTransfers(principal.getName());
    }



    //transfer money method (Transfer transfer)
    //Account account1;
    //Account account2;

    //account1 = account.dao.getAccount(userId);
    //transfer.getUserFrom();

    //transfer.setAmount

    //account1.getAccountID
    //account2.getRecipient




}
