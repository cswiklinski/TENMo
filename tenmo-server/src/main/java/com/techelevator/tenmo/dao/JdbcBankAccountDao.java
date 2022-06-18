package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BankAccount;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBankAccountDao implements BankAccountDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private JdbcTemplate jdbcTemplate;

    public JdbcBankAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> listAccounts() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT account_id, username, user_id FROM account JOIN tenmo_user USING (user_id);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            list.add("Account ID: " + results.getInt("account_id") + " " + "Username: " + results.getString("username"));
        }
        return list;
    }

    @Override
    public BankAccount findByUserId(Long id) {
        String sql = "SELECT account_id, user_id, balance, username FROM account JOIN tenmo_user USING (user_id) WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToBankAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + id + " was not found."); // change to BankAccount exception
    }

    @Override
    public BankAccount findByUserId(BankAccount account) {
        String sql = "SELECT account_id, user_id, balance, username FROM account JOIN tenmo_user USING (user_id) WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, account.getId());
        if (rowSet.next()){
            return mapRowToBankAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + account.getId() + " was not found."); // change to BankAccount exception
    }

    @Override
    public boolean update(BankAccount account) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, account.getBalance(), account.getId());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    private BankAccount mapRowToBankAccount(SqlRowSet rs) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(rs.getLong("account_id"));
        bankAccount.setUserId(rs.getLong("user_id"));
        bankAccount.setBalance(rs.getBigDecimal("balance"));
        bankAccount.setUsername(rs.getString("username"));
        return bankAccount;
    }
}
