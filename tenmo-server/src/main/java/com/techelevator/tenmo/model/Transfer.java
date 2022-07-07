package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private int id;
    @NotBlank(message = "The field 'transferTypeId' is required.")
    private int transferTypeId;
    @NotBlank(message = "The field 'transferStatusId' is required.")
    private int transferStatusId;
    @NotBlank(message = "The field 'accountFromId' is required.")
    private int accountFromId;
    @NotBlank(message = "The field 'accountToId' is required.")
    private int accountToId;
    @Positive(message = "The amount must be more than 0.")
    private BigDecimal amount;
    @NotBlank(message = "The field 'typeDescription' is required")
    private String typeDescription;
    @NotBlank(message = "The field 'statusDescription' is required")
    private String statusDescription;

    public Transfer() {}

    public Transfer(int id, int transferTypeId, int transferStatusId, int accountFromId, int accountToId,
                    BigDecimal amount, String typeDescription, String statusDescription) {
        this.id = id;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
        this.typeDescription = typeDescription;
        this.statusDescription = statusDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
