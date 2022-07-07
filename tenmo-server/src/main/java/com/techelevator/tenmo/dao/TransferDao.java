package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean create(Transfer transfer);

    List<Transfer> displayPastTransfers(int userId);

    Transfer getTransferById(int transferId);

}
