package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.BankAccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.BankAccount;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TransferController {

    RestTemplate restTemplate = new RestTemplate();
    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @GetMapping("/transfer/{id}")
    public Transfer get(@PathVariable Long id) {
        return transferDao.getById(id);
    }

    @GetMapping("/transfers")
    public List<Transfer> getTransfersBySender(@RequestParam Long sender) {
        return transferDao.listBySender(sender);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/transfer")
    public void create(@RequestParam Long sender, Long recipient, BigDecimal amount) {
        transferDao.create(sender, recipient, amount);
    }
}
