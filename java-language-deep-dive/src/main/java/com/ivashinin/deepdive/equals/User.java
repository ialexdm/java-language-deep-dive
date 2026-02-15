package com.ivashinin.deepdive.equals;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public final class User {

    private BigDecimal money;

    private String email;

    private final Long userId;

    User(Long userId, BigDecimal money, String email) {
        this.userId = Objects.requireNonNull(userId);
        this.money = Objects.requireNonNull(money);
        this.email = Objects.requireNonNull(email);
    }


    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email);
    }

    public void setMoney(BigDecimal money) {
        this.money = Objects.requireNonNull(money);
    }

    public BigDecimal getMoney() {
        return money;
    }

    public String getEmail() {
        return email;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "money=" + money +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

         if(!(o instanceof User)){
             return false;
         }
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
