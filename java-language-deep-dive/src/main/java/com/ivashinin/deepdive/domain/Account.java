package com.ivashinin.deepdive.domain;

import com.ivashinin.deepdive.domain.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Objects;



public final class Account {
    private final Long accountId;
    private BigDecimal balance;
    /**
     * @throws NullPointerException if accountId or is are null,
     * @throws IllegalArgumentException if balance bellow zero,
     */
    public Account(Long accountId, BigDecimal balance) {
        Objects.requireNonNull(accountId, "account id should exist");
        Objects.requireNonNull(balance, "balance mustn't be null");
        if (balance.signum() < 0){
            throw new IllegalArgumentException("Balance should not be negative");
        }
        this.accountId = accountId;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (!(obj instanceof Account))
        {
            return false;
        }
        else{
            return this.getAccountId().equals(((Account) obj).getAccountId());
        }
    }
    @Override
    public int hashCode() {
        return this.accountId.hashCode();
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    /**
     *
     * @throws NullPointerException if incomingAmount is null
     * @throws IllegalArgumentException if incomingAmount <= 0
     */
    public void incoming(BigDecimal incomingAmount){
        Objects.requireNonNull(incomingAmount,"Amount shouldn't be null");
        if (incomingAmount.signum() <= 0){
            throw new IllegalArgumentException("Incoming amount should be positive");
        }
        this.balance = balance.add(incomingAmount);
    }

    /**
     *
     * @throws IllegalArgumentException if outgoingAmount is negative
     * @throws InsufficientFundsException if account has not enough balance
     */
    public void outgoing(BigDecimal outgoingAmount){
        Objects.requireNonNull(outgoingAmount,"outgoingAmount shouldn't be null");
        if (outgoingAmount.signum() <= 0){
            throw new IllegalArgumentException("Transferring amount should be positive");
        }
        if (outgoingAmount.compareTo(this.balance) >= 0){
            throw new InsufficientFundsException(this.accountId,this.getBalance(), outgoingAmount);
        }
        this.balance = balance.subtract(outgoingAmount);
    }
}
