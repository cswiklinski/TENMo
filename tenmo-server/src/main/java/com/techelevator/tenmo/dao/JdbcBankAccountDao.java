package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BankAccount;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcBankAccountDao implements BankAccountDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private JdbcTemplate jdbcTemplate;

    public JdbcBankAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BankAccount findByUserId(Long id) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToBankAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + id + " was not found.");
    }

    @Override
    public BankAccount findByUserId(User user) {
        String sql = "SELECT account_id, user_id, balance FROM tenmo_user WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, user.getId());
        if (rowSet.next()){
            return mapRowToBankAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + user.getId() + " was not found.");
    }

    @Override
    public boolean create(Long userId) {
        return false;
    }

    private BankAccount mapRowToBankAccount(SqlRowSet rs) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(rs.getLong("account_id"));
        bankAccount.setUserId(rs.getLong("user_id"));
        bankAccount.setBalance(rs.getBigDecimal("balance"));
        return bankAccount;
    }
}
