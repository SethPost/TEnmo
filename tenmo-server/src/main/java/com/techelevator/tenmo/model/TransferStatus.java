package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;

public class TransferStatus {

    private int id;
    @NotBlank(message = "The field 'description' is required.")
    private String description;

    public TransferStatus() {}

    public TransferStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
