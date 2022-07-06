package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserController {

    private UserDao userDao;
    private AccountDao accountDao;

    public UserController(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) throws AccountNotFoundException {
        return accountDao.displayBalance(id);
    }

}
