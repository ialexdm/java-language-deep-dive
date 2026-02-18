package com.ivashinin.deepdive.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {

    private Money usd(BigDecimal amount){
        return  new Money(amount, Currency.getInstance("USD"));
    }
    private Money eur(BigDecimal amount){
        return  new Money(amount, Currency.getInstance("EUR"));
    }
    @Test
    public void equals_sameAmountAndCurrency_shouldBeEqual(){
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 = usd(new BigDecimal("10.00"));
        assertTrue(money1.equals(money2));
    }
    @Test
    public void equals_sameAmountAndDifferentScale_shouldBeEqual(){
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 = usd(new BigDecimal("10.0"));
        assertTrue(money1.equals(money2));
    }

    @Test
    public void equals_differentCurrency_shouldNotBeEqual(){
        Money usd = usd(new BigDecimal("10.00"));
        Money eur = eur(new BigDecimal("10.00"));
        Assertions.assertFalse(usd.equals(eur));
    }

    @Test
    public void equals_is_symmetric(){
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 =usd(new BigDecimal("10.00"));

        assertTrue(money1.equals(money2));
        assertTrue(money2.equals(money1));

        assertTrue(money1.hashCode() == money2.hashCode());
    }
    @Test
    public void equals_is_reflexive() {
    Money money1 = usd(new BigDecimal("10.00"));
    Money money2 = money1;
    assertTrue(money1.equals(money2));
    }

    @Test
    public void equals_is_transitive(){
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 =usd(new BigDecimal("10.00"));
        Money money3 = usd(new BigDecimal("10.00"));
        assertTrue(money2.equals(money3));
        assertTrue(money1.equals(money3));
        assertTrue(money1.equals(money2));
    }

    @Test
    void equal_objects_have_same_hashcode() {
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 =usd(new BigDecimal("10.00"));
        assertEquals(money1,money2);
        assertEquals(money1.hashCode(), money2.hashCode());
    }
    @Test
    void equals_null_shouldReturnFalse() {
        Money money = usd(new BigDecimal("10.00"));
        assertNotEquals(money, null);
    }

    @Test
    void equals_otherType_shouldReturnFalse() {
        Money money = usd(new BigDecimal("10.00"));
        assertNotEquals(money, "10 USD");
    }



    @Test
    public void constructor_withNullAmount_shouldThrowNPE(){
        Assertions.assertThrows(NullPointerException.class, () ->{
            new Money(null, Currency.getInstance("USD"));
        });
    }
    @Test
    public void constructor_withNullCurrency_shouldThrowNPE(){
        Assertions.assertThrows(NullPointerException.class, () ->{
            new Money(new BigDecimal("10.00"), null);
        });
    }
    @Test
    public void constructor_withUnderZeroAmount_shouldThrowIAE(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            new Money(new BigDecimal("-10.00"), Currency.getInstance("USD"));
        });
    }
    @Test
    public void BY_AMOUNT_SAME_CURRENCY_shouldSortByAmount(){
        Money money1 = usd(new BigDecimal("5.00"));
        Money money2 =usd(new BigDecimal("10.00"));
        Comparator<Money> comparator = Money.BY_AMOUNT_SAME_CURRENCY;
        assertTrue(comparator.compare(money1,money2)<0);
    }
    @Test
    public void BY_AMOUNT_SAME_CURRENCY_withDifferentAmount_shouldTrowIAE(){
        Money usd = usd(new BigDecimal("5.00"));
        Money eur = eur(new BigDecimal("10.00"));

        Comparator<Money> comparator = Money.BY_AMOUNT_SAME_CURRENCY;
        Assertions.assertThrows(IllegalArgumentException.class, () -> comparator.compare(usd,eur));
    }
    @Test
    void BY_CURRENCY_THEN_AMOUNT_shouldSortByCurrencyFirst() {
        Money usd = usd(new BigDecimal("5.00"));
        Money eur = eur(new BigDecimal("10.00"));

        Comparator<Money> comparator = Money.BY_CURRENCY_THEN_AMOUNT;

        assertTrue(comparator.compare(eur, usd) < 0);
        assertTrue(comparator.compare(usd, eur) > 0);
    }

    @Test
    void BY_CURRENCY_THEN_AMOUNT_sameCurrency_shouldSortByAmount() {
        Money usd = usd(new BigDecimal("5.00"));
        Money eur = usd(new BigDecimal("10.00"));

        Comparator<Money> comparator = Money.BY_CURRENCY_THEN_AMOUNT;

        assertTrue(comparator.compare(usd, eur) < 0);
    }
    @Test
    void BY_CURRENCY_THEN_AMOUNT_equalObjects_shouldReturnZero() {
        Money m1 = usd(new BigDecimal("10.00"));
        Money m2 = usd(new BigDecimal("10.00"));

        Comparator<Money> comparator = Money.BY_CURRENCY_THEN_AMOUNT;

        assertEquals(0, comparator.compare(m1, m2));
    }
    @Test
    void shouldSortListCorrectly() {
        List<Money> list = List.of(
                usd(new BigDecimal("10")),
                eur(new BigDecimal("5")),
                usd(new BigDecimal("3")),
                eur(new BigDecimal("1"))
        );

        List<Money> sorted = new ArrayList<>(list);
        sorted.sort(Money.BY_CURRENCY_THEN_AMOUNT);

        assertEquals(
                List.of(
                        eur(new BigDecimal("1")),
                        eur(new BigDecimal("5")),
                        usd(new BigDecimal("3")),
                        usd(new BigDecimal("10"))
                ),
                sorted
        );
    }

    @Test
    public void add_sameCurrency_returnAddedMoney()
    {
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 = usd(new BigDecimal("1.00"));
        Assertions.assertEquals(usd(new BigDecimal("11.00")), money1.add(money2));
    }
    @Test
    public void add_differentCurrency_throwsIAE()
    {
        Money usd = usd(new BigDecimal("10.00"));
        Money eur = eur(new BigDecimal("1.00"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> usd.add(eur));
    }
    @Test
    public void subtract_sameCurrency_returnSubtractedMoney()
    {
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 = usd(new BigDecimal("10.00"));
        Assertions.assertEquals(usd(new BigDecimal("0.00")), money1.subtract(money2));
    }
    @Test
    public void subtract_differentCurrency_throwsIAE()
    {
        Money usd = usd(new BigDecimal("10.00"));
        Money eur = eur(new BigDecimal("1.00"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> usd.subtract(eur));
    }
    @Test
    public void subtract_reducedLessThanDeducted_throwsIAE()
    {
        Money money1 = usd(new BigDecimal("10.00"));
        Money money2 = usd(new BigDecimal("1.00"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> money2.subtract(money1));
    }

    @Test
    public void multiply_returnMultipliedMoney()
    {

        Money money1 = usd(new BigDecimal("10.00"));
        int multiplier = 3;
        Assertions.assertEquals(usd(new BigDecimal("30.00")), money1.multiply(new BigDecimal(multiplier)));
    }
    @Test
    public void multiply_factorIsNull_thenThrowsNPE()
    {

        Money money1 = usd(new BigDecimal("10.00"));

        Assertions.assertThrows(NullPointerException.class, () -> money1.multiply(null));
    }

    @Test
    public void shouldRemainInTreeSetAfterOperations(){
        Money money = usd(new BigDecimal("10.00"));
        Money money2 = usd(new BigDecimal("1.00"));
        Money money3 = usd(new BigDecimal("5.00"));
        TreeSet<Money> moneyTreeSet = new TreeSet<>(Money.BY_AMOUNT_SAME_CURRENCY);
        moneyTreeSet.add(money);
        Money money4 = money.add(money2).subtract(money3).multiply(new BigDecimal("3"));
        assertTrue(moneyTreeSet.contains(usd(new BigDecimal("10.00"))));
        assertFalse(moneyTreeSet.contains(money4));
    }



}
