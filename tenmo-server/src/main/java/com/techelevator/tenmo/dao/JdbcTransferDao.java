package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer send(int accountFromId, int accountToId, BigDecimal amount) {
        Transfer transfer = new Transfer();

        return transfer;
    }

    @Override
    public List<Transfer> displayPastTransfers() {
        List<Transfer> transfers = new ArrayList<>();

        return transfers;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, account_from, account_to, amount, " +
                "transfer_type.transfer_type_id, transfer_type_desc, transfer_status.transfer_status_id, transfer_status_desc " +
                "FROM tenmo_transfer " +
                "JOIN transfer_type ON tenmo_transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "JOIN transfer_status ON tenmo_transfer.transfer_status_id = transfer_status.transfer_status_id " +
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setId(rowSet.getInt("transfer_id"));
        transfer.setAccountFromId(rowSet.getInt("account_from"));
        transfer.setAccountToId(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        return transfer;
    }
}
