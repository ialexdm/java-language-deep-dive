package com.ivashinin.deepdive.domain.service;

import com.ivashinin.deepdive.domain.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransferServiceTest {
    private TransferService transferService;
    @BeforeEach
    public void initTransferService(){
        transferService = new TransferServiceImpl();
    }


    private Account firstTestAccount(){
        return new Account(1L, new BigDecimal("20"));
    }
    private Account secondTestAccount(){
        return new Account(2L, new BigDecimal("20"));
    }


    @Test
    public void transfer_WithSameAccounts_shouldThrows(){
        Account from = firstTestAccount();
        Account to = from;
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                transferService.transfer(from, to, new BigDecimal("10"))
                );
    }
    @Test
    public void transfer_WithEqualAccounts_shouldThrows(){
        Account from = firstTestAccount();
        Account to = firstTestAccount();
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                transferService.transfer(from, to, new BigDecimal("10"))
        );
    }
    @Test
    public void transfer_WithDiferentAccounts_shouldTransfer(){
        Account from = firstTestAccount();
        Account to = secondTestAccount();
        transferService.transfer(from, to, new BigDecimal("10"));
        Assertions.assertEquals(new BigDecimal("10"), from.getBalance());
        Assertions.assertEquals(new BigDecimal("30"), to.getBalance());
    }
    @Test
    public void transfer_WithNullAccountTo_shouldThrows(){
        Account from = firstTestAccount();
        Account to = null;
        Assertions.assertThrows(NullPointerException.class, () ->
                transferService.transfer(from, to, new BigDecimal("10"))
        );
    }
    @Test
    public void transfer_WithNullAccountFrom_shouldThrows(){
        Account from = null;
        Account to = firstTestAccount();
        Assertions.assertThrows(NullPointerException.class, () ->
                transferService.transfer(from, to, new BigDecimal("10"))
        );
    }
    @Test
    public void transfer_WithNullAmount_shouldThrows(){
        Account from = firstTestAccount();
        Account to = secondTestAccount();
        Assertions.assertThrows(NullPointerException.class, () ->
                transferService.transfer(from, to, null)
        );
    }

}
