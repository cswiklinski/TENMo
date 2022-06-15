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
            ResponseEntity<BankAccount> response = restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), BankAccount.class);
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

//    public boolean transfer(Long id, Long recipientId, BigDecimal amount) {
//        boolean success = false;
//        try {
//            ResponseEntity<BankAccount> response = restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), BankAccount.class);
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        try {
//            restTemplate.put(API_BASE_URL + id + "/transfer?recipient=" + recipientId + "&amount=" + amount, HttpMethod.PUT, makeAuthEntity(), Void.class);
//            success = true;
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return success;
//    }


    private HttpEntity<BankAccount> makeAccountEntity(BankAccount account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
