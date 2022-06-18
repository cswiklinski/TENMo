package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean create(Transfer transfer, int transferType, int transferStatus);

    List<Transfer> listBySender(Long senderId);

    Transfer getById(Long transferId);

    List<Transfer> listAllTransfers();

    boolean update(Transfer transfer);
}
