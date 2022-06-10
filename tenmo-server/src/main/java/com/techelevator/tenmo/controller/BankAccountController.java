package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.BankAccountDao;
import com.techelevator.tenmo.dao.JdbcBankAccountDao;
import com.techelevator.tenmo.model.BankAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/account/")
public class BankAccountController {

    RestTemplate restTemplate = new RestTemplate();
    private BankAccountDao accountDao;

    public BankAccountController(BankAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping("{id}")
    public BankAccount get(@PathVariable Long id) {
        return accountDao.findByUserId(id);
    }
}
