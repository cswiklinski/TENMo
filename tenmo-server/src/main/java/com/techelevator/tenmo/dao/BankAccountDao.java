package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BankAccount;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountDao {

    BankAccount findByUserId(Long id);

    BankAccount findByUserId(User user);

//    boolean send(Long senderId, Long receiverId, BigDecimal amount);

    boolean update(BankAccount account);

    List<String> listAccounts();
}
