package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountNotFoundException;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal displayBalance(int accountId) throws AccountNotFoundException;

    boolean updateAdd(int accountId, Transfer transfer, Account account) throws AccountNotFoundException;


    boolean updateSubtract(int accountId, Transfer transfer, Account account) throws AccountNotFoundException;


}
