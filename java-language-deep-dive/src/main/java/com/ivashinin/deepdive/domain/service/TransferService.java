package com.ivashinin.deepdive.domain.service;

import com.ivashinin.deepdive.domain.Account;

import java.math.BigDecimal;

public interface TransferService {
    void transfer(Account from, Account to, BigDecimal amount);
}
