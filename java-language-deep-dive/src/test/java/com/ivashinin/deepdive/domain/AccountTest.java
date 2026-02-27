package com.ivashinin.deepdive.domain;

import com.ivashinin.deepdive.domain.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AccountTest {

    private Account testAccount(String balance) {
        return new Account(1L, new BigDecimal(balance));
    }

    @Test
    public void constructor_withNegativeBalance_shouldThrows(){
        Assertions.assertThrows(
                IllegalArgumentException.class , () ->
        testAccount("-30"));
    }
    @Test
    public void constructor_withNullBalance_shouldThrows(){
        Assertions.assertThrows(
                NullPointerException.class , () ->
                        testAccount(null));
    }
    @Test
    public void constructor_withNullAccountId_shouldThrows(){
        Assertions.assertThrows(
                NullPointerException.class , () ->
                        new Account(null, new BigDecimal("10")));
    }

    @Test
    public void constructor_withPositiveBalance_shouldWork(){
        Assertions.assertDoesNotThrow( () ->
                testAccount("30"));
    }
    @Test
    public void constructor_withZeroBalance_shouldWork(){
        Assertions.assertDoesNotThrow( () ->
                testAccount("0"));
    }
    @Test
    public void incoming_2ithNegativeAmount_shouldThrows()
    {
        Account account = testAccount("10");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                account.incoming(new BigDecimal("-10")));
    }
    @Test
    public void incoming_2ithZeroAmount_shouldThrows()
    {
        Account account = testAccount("10");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                account.incoming(new BigDecimal("0")));
    }
    @Test
    public void incoming_withNullAmount_shouldThrows(){
        Assertions.assertThrows(
                NullPointerException.class , () ->
                        testAccount("10").incoming(null));
    }
    @Test
    public void incoming_withPositiveAmount_shouldIncreaseBalance(){
        Account account = testAccount("10");
        account.incoming(new BigDecimal("10"));
        Assertions.assertEquals(new BigDecimal("20"), account.getBalance());
    }

    @Test
    public void outgoing_withNegativeAmount_shouldThrows()
    {
        Account account = testAccount("10");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                account.outgoing(new BigDecimal("-10")));
    }
    @Test
    public void outgoing_withZeroAmount_shouldThrows()
    {
        Account account = testAccount("10");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                account.outgoing(new BigDecimal("0")));
    }
    @Test
    public void outgoing_withPositiveAmount_shouldReduceBalance(){
        Account account = testAccount("20");
        account.outgoing(new BigDecimal("10"));
        Assertions.assertEquals(new BigDecimal("10"), account.getBalance());
    }

    @Test
    public void outgoing_withInsufficientBalance_shouldThrow(){
        Account account = testAccount("10");
        Assertions.assertThrows(InsufficientFundsException.class, () ->
                account.outgoing(new BigDecimal("20"))
        );
    }
    @Test
    public void outgoing_withNullAmount_shouldThrow(){
        Assertions.assertThrows(
                NullPointerException.class , () ->
                        testAccount("10").outgoing(null));
    }
    @Test
    public void equals_is_reflexive(){
        Account account1 = testAccount("10");
        Account account2 = account1;
        Assertions.assertEquals(account1, account2);
    }
    @Test
    public void equals_symmetric_true(){
        Account account1 = testAccount("10");
        Account account2 = testAccount("20");
        Assertions.assertEquals(account1, account2);
        Assertions.assertEquals(account2, account1);
    }
    @Test
    public void equals_transistive_true(){
        Account account1 = testAccount("10");
        Account account2 = testAccount("20");
        Account account3 = testAccount("30");
        Assertions.assertEquals(account1, account2);
        Assertions.assertEquals(account2, account3);
        Assertions.assertEquals(account1, account3);
    }
    @Test
    public void equals_returns_false_when_null() {
        Assertions.assertNotEquals(testAccount("10"), null);
    }

    @Test
    public void equals_returns_false_for_different_type() {
        Assertions.assertNotEquals(testAccount("10"), "not an Account");
    }
    @Test
    public void equal_acounts_have_same_hashcode() {
        Account account1 = testAccount("10");
        Account account2 = testAccount("20");
        Assertions.assertEquals(account1,account2);
        Assertions.assertEquals(account1.hashCode(), account2.hashCode());
    }
    @Test
    public void equals_returnFalse_withDiferentAccountId(){
        Account account1 = testAccount("10");
        Account account2 = new Account(2L, new BigDecimal("10"));
        Assertions.assertNotEquals(account1,account2);
    }


}
