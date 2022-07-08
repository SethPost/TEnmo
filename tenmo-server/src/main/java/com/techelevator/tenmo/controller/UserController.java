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
import java.security.Principal;
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
    public Account getAccount(@PathVariable int accountId, Principal user) throws AccountNotFoundException {

        Account searchedAccount = accountDao.getAccount(accountId);
        int userId = searchedAccount.getUserId();
        User searchedUser = userDao.getUserById(userId);
        String searchedUsername = searchedUser.getUsername();

        String username = user.getName();
        if (username.equals(searchedUsername)) {
            return accountDao.getAccount(accountId);
        } else {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(path = "/user/account/balance/{accountId}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int accountId, Principal user) throws AccountNotFoundException {
        Account searchedAccount = accountDao.getAccount(accountId);
        int userId = searchedAccount.getUserId();
        User searchedUser = userDao.getUserById(userId);
        String searchedUsername = searchedUser.getUsername();

        String username = user.getName();
        if (username.equals(searchedUsername)) {
            return accountDao.displayBalance(accountId);
        } else {
            throw new ForbiddenException();
        }
    }


    //Start use case 4

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/user/account/transfer", method = RequestMethod.POST)
    public Transfer sendTransfer(@Valid @RequestBody Transfer newTransfer, Principal user) {
        String username = user.getName();
        Account accountTo = accountDao.getAccount(newTransfer.getAccountToId());
        Account accountFrom = accountDao.getAccount(newTransfer.getAccountFromId());

        int fromUserId = accountFrom.getUserId();
        User searchedUser = userDao.getUserById(fromUserId);
        String searchedUsername = searchedUser.getUsername();

        if (username.equals(searchedUsername)) {
            BigDecimal difference = accountFrom.getBalance().subtract(newTransfer.getAmount());
            if (newTransfer.getAccountToId() != newTransfer.getAccountFromId()
                    && difference.compareTo(new BigDecimal("0.00")) >= 0
                    && newTransfer.getAmount().compareTo(new BigDecimal("0.00")) > 0) {
                transferDao.create(newTransfer);
                accountDao.updateAdd(newTransfer, accountTo);
                accountDao.updateSubtract(newTransfer, accountFrom);
                return newTransfer;
            } else {
                return newTransfer = null;
            }
        } else {
            throw new ForbiddenException();
        }
    }


    //End use case 4

    @RequestMapping(path = "/transfer/list/{userId}", method = RequestMethod.GET)
    public List<Transfer> listTransfers(@PathVariable int userId, Principal user) throws UserNotFoundException {
        String username = user.getName();

        User searchedUser = userDao.getUserById(userId);
        String searchedUsername = searchedUser.getUsername();

        if(username.equals(searchedUsername)) {
            return transferDao.displayPastTransfers(userId);
        } else {
            throw new ForbiddenException();
        }
    }


    //Fix column error between this and getTransferById() in JdbcTransferDao
    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId, Principal user) throws TransferNotFoundException {
        String username = user.getName();

        Transfer searchedTransfer = transferDao.getTransferById(transferId);
        String searchedFromUsername = searchedTransfer.getFromUsername();
        String searchedToUsername = searchedTransfer.getToUsername();

        if(username.equals(searchedFromUsername) || username.equals(searchedToUsername)) {
            return transferDao.getTransferById(transferId);
        } else {
            throw new ForbiddenException();
        }
    }

}
