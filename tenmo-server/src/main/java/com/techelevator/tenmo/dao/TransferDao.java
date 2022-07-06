package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer send(int accountFromId, int accountToId, BigDecimal amount);

    List<Transfer> displayPastTransfers();

    Transfer getTransferById(int transferId);

}
