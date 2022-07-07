package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal displayBalance(int accountId) {
        BigDecimal currentBalance;

        String sql = "SELECT balance FROM tenmo_account WHERE account_id = ?";
        currentBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);

        return currentBalance;
    }

    @Override
    public boolean updateAdd(int accountId, Transfer transfer, Account account) throws AccountNotFoundException {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        return jdbcTemplate.update(sql, (account.getBalance().add(transfer.getAmount())), accountId) == 1;
    }

    @Override
    public boolean updateSubtract(int accountId, Transfer transfer, Account account) throws AccountNotFoundException {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        return jdbcTemplate.update(sql, (account.getBalance().subtract(transfer.getAmount())), accountId) == 1;
    }

}
