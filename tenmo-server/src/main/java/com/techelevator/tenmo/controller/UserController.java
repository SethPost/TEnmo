package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class UserController {

    private UserDao userDao;
    private AccountDao accountDao;
    private TransferDao transferDao;

    public UserController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) throws AccountNotFoundException {
        return accountDao.displayBalance(id);
    }

    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) throws TransferNotFoundException {
        return transferDao.getTransferById(id);
    }

    //Start use case 4

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void createNewTransfer(@Valid @RequestBody Transfer newTransfer)
    {transferDao.create(newTransfer);}
    //Is there a way to call the transfer created in the above method in the method below?

    @RequestMapping(path = "/account/{id}", method = RequestMethod.PUT)
    public void updateAdd(@Valid Transfer transfer, @RequestBody Account updatedAccount, @PathVariable int id) {
        accountDao.updateAdd(id, transfer, updatedAccount);
    }

    //End use case 4

}
