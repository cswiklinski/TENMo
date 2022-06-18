package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {
    private static final String BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken= null;

    public void setAuthToken(String authToken){
        this.authToken=authToken;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, httpHeaders);
    }

    public boolean send(Transfer transfer){
        boolean success = false;

        try {
            restTemplate.put(BASE_URL + "transfer", makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }
        return success;
    }

    public boolean request(Transfer transfer) {
        boolean success = false;

        try {
            restTemplate.put(BASE_URL + "transfer?type=" + 1 + "&status=" + 1, makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }
        return success;
    }

    public List<Transfer> listTransfers(long account) {
        List<Transfer> transferList = new ArrayList<>();
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "transfers/" + account, HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transferList = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferList;
    }

    public List<Transfer> listTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getById(long id) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(BASE_URL + "/transfer/" + id, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public boolean update(Transfer transfer) {
        boolean success = false;
        try {
            restTemplate.put(BASE_URL + "transfer/" + transfer.getId(), makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
