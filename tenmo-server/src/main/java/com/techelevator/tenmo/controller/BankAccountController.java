package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.BankAccountDao;
import com.techelevator.tenmo.dao.JdbcBankAccountDao;
import com.techelevator.tenmo.model.BankAccount;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account/")
@PreAuthorize("isAuthenticated()")
public class BankAccountController {

    RestTemplate restTemplate = new RestTemplate();
    private BankAccountDao accountDao;

    public BankAccountController(BankAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping("")
    public List<String> listAccounts() {
        return accountDao.listAccounts();
    }

    @GetMapping("{id}")
    public BankAccount get(@PathVariable Long id) {
        return accountDao.findByUserId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    public BankAccount update(@RequestBody BankAccount account, @PathVariable Long id) {
        accountDao.update(account);
        return account;
    }

//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("{id}/transfer")
//    public boolean transfer(@PathVariable Long id,
//                            @RequestParam(name = "recipient", required = true) Long recipientId,
//                            @RequestParam(name="amount", defaultValue="0")BigDecimal amount) {
//        return accountDao.send(id, recipientId, amount);
//    }
}
