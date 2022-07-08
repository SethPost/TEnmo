package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.RowSet;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account getAccount(int accountId) throws AccountNotFoundException {
        Account account = new Account();

        String sql = "SELECT account_id, user_id, balance FROM tenmo_account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public BigDecimal displayBalance(int accountId) throws AccountNotFoundException{
        BigDecimal currentBalance;

        String sql = "SELECT balance FROM tenmo_account WHERE account_id = ?;";
        currentBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);

        return currentBalance;
    }

    @Override
    public boolean updateAdd(Transfer transfer, Account account) throws AccountNotFoundException {
        int accountId = transfer.getAccountToId();
        String sql = "UPDATE tenmo_account SET balance = ? WHERE account_id = ?;";
        return jdbcTemplate.update(sql, (account.getBalance().add(transfer.getAmount())), accountId) == 1;
    }

    @Override
    public boolean updateSubtract(Transfer transfer, Account account) throws AccountNotFoundException {
        int accountId = transfer.getAccountFromId();
        String sql = "UPDATE tenmo_account SET balance = ? WHERE account_id = ?;";
        return jdbcTemplate.update(sql, (account.getBalance().subtract(transfer.getAmount())), accountId) == 1;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
