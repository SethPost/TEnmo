package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountNotFoundException;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal displayBalance(int accountId) throws AccountNotFoundException;


}
