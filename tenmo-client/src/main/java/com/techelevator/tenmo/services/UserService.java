package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }
    public User[] findAllUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(API_BASE_URL + "user/list", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public User getUserById(int userId) {
        User user = null;
        try {
            ResponseEntity<User> response =
                    restTemplate.exchange(API_BASE_URL + "user/" + userId,
                            HttpMethod.GET, makeAuthEntity(), User.class);
            user = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }


    public Account getAccount(int accountId) {
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "user/account/" + accountId,
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public BigDecimal getBalance(int accountId) {
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response =
                    restTemplate.exchange(API_BASE_URL + "user/account/balance/" + accountId,
                            HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public Transfer sendTransfer(Transfer newTransfer) {
        Transfer returnedTransfer = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity(newTransfer, headers);

        String url = API_BASE_URL + "user/account/transfer";

        returnedTransfer = restTemplate.postForObject(url, entity, Transfer.class);

        return returnedTransfer;
    }

    public Transfer[] listTransfers(int userId) {
        Transfer[] transfers = null;
        try {
            String url = API_BASE_URL + "transfer/list/" + userId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity entity = new HttpEntity<>(headers);

            ResponseEntity<Transfer[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer[].class);

            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(API_BASE_URL + "transfer/" + transferId,
                            HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }
}
