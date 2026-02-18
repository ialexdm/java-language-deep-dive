package com.ivashinin.deepdive.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Currency;
import java.util.Objects;

public final class Money {
    private final BigDecimal amount;
    private final Currency currency;
    public static final Comparator<Money> BY_AMOUNT_SAME_CURRENCY = (m1, m2) -> {
                if (!m1.getCurrency().equals(m2.getCurrency())){
                    throw new IllegalArgumentException("Currency must be same");
                }
             return m1.getAmount().compareTo(m2.getAmount());
            };
    public static final Comparator<Money> BY_CURRENCY_THEN_AMOUNT = Comparator.comparing(Money::getCurrency, Comparator.comparing(Currency::getCurrencyCode)).thenComparing(Money::getAmount);

    public Money(BigDecimal amount, Currency currency) {
        Objects.requireNonNull(amount, "Amount must not be null");
        Objects.requireNonNull(currency, "Currency must not be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must not be negative");
        }

        int scale = currency.getDefaultFractionDigits();
        if (scale < 0) {
            throw new IllegalArgumentException("Unsupported currency scale");
        }

        this.currency = currency;
        this.amount = amount.setScale(scale, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;
        return amount.equals(money.amount)
                && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }
    public Money add(Money other){
        if (!other.currency.equals(this.currency))
        {
            throw new IllegalArgumentException("Currency must be same");
        }
        Money result = new Money(other.amount.add(this.amount), this.currency);
        return result;
    }
    public Money subtract(Money other){
        if (!other.currency.equals(this.currency))
        {
            throw new IllegalArgumentException("Currency must be same");
        }
        if (BY_AMOUNT_SAME_CURRENCY.compare(other,this) > 0)
        {
            throw new IllegalArgumentException("Resulting amount must not be negative");
        }
        Money result = new Money(this.amount.subtract(other.amount), this.currency);
        return result;
    }
    public Money multiply(BigDecimal factor){
        Money result = new Money(this.amount.multiply(Objects.requireNonNull(factor)), this.currency);
        return result;
    }

}
