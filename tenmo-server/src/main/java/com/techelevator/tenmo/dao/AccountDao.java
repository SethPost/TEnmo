package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserNotFoundException;

import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount(int accountId) throws AccountNotFoundException;

    Account getAccountByUserId (int userId) throws UserNotFoundException;

    BigDecimal displayBalance(int accountId) throws AccountNotFoundException;

    boolean updateAdd(Transfer transfer, Account account) throws AccountNotFoundException;


    boolean updateSubtract(Transfer transfer, Account account) throws AccountNotFoundException;


}
