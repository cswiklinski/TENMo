package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean create(Long senderId, Long recipientId, BigDecimal amount);

    List<Transfer> listBySender(Long senderId);

    Transfer getById(Long transferId);
}
