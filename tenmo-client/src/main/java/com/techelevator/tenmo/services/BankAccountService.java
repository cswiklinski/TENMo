package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.BankAccount;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankAccountService {

    public static final String API_BASE_URL = "http://localhost:8080/account/";
    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    // set the JWT token
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<String> listAccounts() {
        List<String> accountList = new ArrayList<>();
        try {
            ResponseEntity<String[]> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), String[].class);
            accountList = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountList;
    }

    public BankAccount get(Long id) {
        BankAccount account = null;
        try {
            ResponseEntity<BankAccount> response =
                    restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), BankAccount.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public boolean update(BankAccount account) {
        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + account.getId(), makeAccountEntity(account));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    // turns the BankAccount object passed in the arguments into the body of an HTTP request, including the JWT token in the header
    private HttpEntity<BankAccount> makeAccountEntity(BankAccount account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    // includes the JWT token in the header for get requests
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
