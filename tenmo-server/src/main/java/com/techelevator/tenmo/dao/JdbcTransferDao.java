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
    public boolean create(Transfer transfer) {
        // STEP 1 - Declare whatever variable you want to return


        // STEP 2 - Write out the SQL we want to execute and save it to a string
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, " +
                     "account_to, amount) " +
                     "VALUES (DEFAULT, ?, ?, ?, ?, ?);";

        // STEP 3 - Send the SQL to the database and then store the results if necessary
        //         a) If we expect multiple rows or columns coming back (a spreadsheet) then we use jdbcTemplate.queryForRowSet
        //         b) If we want only one result (one row, one column - literally just one thing), we can use jdbcTemplate.queryForObject
        //         c) If we are doing an update or delete, we use jdbcTemplate.update

        // STEP 4 - If we have results and need to transfer them to objects, do that here

        // STEP 5 - Return the result if necessary
        return jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFromId(),
            transfer.getAccountToId(), transfer.getAmount()) == 1;
    }

    @Override
    public List<Transfer> displayPastTransfers() {
        // STEP 1 - Declare whatever variable you want to return
        List<Transfer> transfers = new ArrayList<>();

        // STEP 2 - Write out the SQL we want to execute and save it to a string

        // STEP 3 - Send the SQL to the database and then store the results if necessary
        //         a) If we expect multiple rows or columns coming back (a spreadsheet) then we use jdbcTemplate.queryForRowSet
        //         b) If we want only one result (one row, one column - literally just one thing), we can use jdbcTemplate.queryForObject
        //         c) If we are doing an update or delete, we use jdbcTemplate.update

        // STEP 4 - If we have results and need to transfer them to objects, do that here

        // STEP 5 - Return the result if necessary

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
        transfer.setTypeDescription(rowSet.getString("transfer_type_desc"));
        transfer.setStatusDescription(rowSet.getString("transfer_status_desc"));
        return transfer;
    }
}
