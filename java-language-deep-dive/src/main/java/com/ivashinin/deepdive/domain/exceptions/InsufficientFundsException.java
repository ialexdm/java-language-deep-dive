package com.ivashinin.deepdive.domain.exceptions;

import java.math.BigDecimal;


public class InsufficientFundsException extends IllegalStateException{
    private final Long accountId;
    private final BigDecimal balance;
    private final BigDecimal amount;
    /**
     *
     * @param accountId - accountID Account which send funds
     * @param balance - balance account which send funds
     * @param amount - Transferring funds
     */

            public InsufficientFundsException(Long accountId,
                                              BigDecimal balance,
                                              BigDecimal amount) {
                super(String.format(
                        "Insufficient funds: accountId=%s, balance=%s, attempted=%s",
                        accountId, balance, amount
                ));

                this.accountId = accountId;
                this.balance = balance;
                this.amount = amount;
            }

            public Long getAccountId() {
                return accountId;
            }

            public BigDecimal getBalance() {
                return balance;
            }

            public BigDecimal getAmount() {
                return amount;
            }
        }
