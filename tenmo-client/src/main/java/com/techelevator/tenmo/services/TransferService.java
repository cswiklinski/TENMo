package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private static final String BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken= null;

    public void setAuthToken(String authToken){
        this.authToken=authToken;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, httpHeaders);
    }

    public boolean create(Transfer transfer){
        boolean success = false;

        try {
            restTemplate.put(BASE_URL + "transfer", makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }
        return success;
    }

}
