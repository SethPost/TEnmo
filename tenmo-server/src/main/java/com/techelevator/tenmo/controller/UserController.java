package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;
    private AccountDao accountDao;
    private TransferDao transferDao;

    public UserController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(path = "user/account/{accountId}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int accountId) throws AccountNotFoundException {
        return accountDao.getAccount(accountId);
    }

    @RequestMapping(path = "/user/account/balance/{accountId}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int accountId) throws AccountNotFoundException {
        return accountDao.displayBalance(accountId);
    }


    //Start use case 4

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/user/account/transfer", method = RequestMethod.POST)
    public Transfer createNewTransfer(@Valid @RequestBody Transfer newTransfer) {
        transferDao.create(newTransfer);
        return newTransfer;
    }
    //Is there a way to call the transfer created in the above method in the method below?

    @RequestMapping(path = "/user/account/add/{accountId}", method = RequestMethod.PUT)
    public void updateAdd(@Valid Transfer transfer, @RequestBody Account updatedAccount, @PathVariable int accountId) throws AccountNotFoundException {
        accountDao.updateAdd(transfer, updatedAccount);
    }

    @RequestMapping(path = "/user/account/subtract/{accountId}", method = RequestMethod.PUT)
    public void updateSubtract(@Valid Transfer transfer, @RequestBody Account updatedAccount, @PathVariable int accountId) throws AccountNotFoundException {
        accountDao.updateSubtract(transfer, updatedAccount);
    }

    //End use case 4

    @RequestMapping(path = "/transfer/list/{userId}", method = RequestMethod.GET)
    public List<Transfer> listTransfers(@PathVariable int userId) throws UserNotFoundException {
        return transferDao.displayPastTransfers(userId);
    }


    //Fix column error between this and getTransferById() in JdbcTransferDao
    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId) throws TransferNotFoundException {
        return transferDao.getTransferById(transferId);
    }

}
