package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BankAccount;
import com.techelevator.tenmo.model.User;

public interface BankAccountDao {

    BankAccount findByUserId(Long id);

    BankAccount findByUserId(User user);

    boolean create(Long userId);
}
