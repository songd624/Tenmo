package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

        @Autowired
        private UserDao userDao;
        @Autowired
        private AccountDao accountDao;

        public AccountController(UserDao userDao, AccountDao accountDao) {
                this.userDao = userDao;
                this.accountDao = accountDao;
        }

        public AccountController() {
        }

        @RequestMapping(path = "/account", method = RequestMethod.GET)
        public Account getBalance(Principal principal) {
                String name = principal.getName();
                String username = userDao.findByUsername(name).getUsername();
                return accountDao.getAccount(username);
        }


        //Do we need to do add and subtract here as well?
}
