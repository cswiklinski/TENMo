package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.BankAccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.BankAccount;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
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

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/transfer/{id}")
    public boolean update(@RequestBody Transfer transfer) {
        return transferDao.update(transfer);
    }

    @GetMapping("/transfers/{id}")
    public List<Transfer> getTransfersBySender(@PathVariable Long id) {
        return transferDao.listBySender(id);
    }

    @GetMapping("/transfers")
    public List<Transfer> getAllTransfers() {
        return transferDao.listAllTransfers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/transfer")
    public void create(@RequestBody Transfer transfer,
                       @RequestParam(name = "type", required = false, defaultValue = "2") int transferType,
                       @RequestParam(name = "status", required = false, defaultValue = "2") int transferStatus) {
        transferDao.create(transfer, transferType, transferStatus);
    }
}
