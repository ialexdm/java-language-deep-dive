package com.ivashinin.deepdive.domain.service;

import com.ivashinin.deepdive.domain.Account;
import com.ivashinin.deepdive.domain.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Transfers money between accounts.
 *
 * @throws IllegalArgumentException if accounts are same or amount <= 0
 * @throws InsufficientFundsException if the source account has not enough balance
 */

public class TransferServiceImpl implements TransferService{
    @Override
    public void transfer(Account from, Account to, BigDecimal amount) {

        Objects.requireNonNull(from, "from account must not be null");
        Objects.requireNonNull(to, "to account must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        if (from == to || from.equals(to)){
            throw new IllegalArgumentException("Account must be different");
        }
        from.outgoing(amount);
        to.incoming(amount);
    }
}
