package com.techelevator.tenmo.dao;

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

}
