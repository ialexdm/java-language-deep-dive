package com.ivashinin.deepdive.equals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class EqualsContarctTests {
    User first = new User(1L, new BigDecimal("100.50"), "user1@mail.net");
    User same = first;
    User second = new User(1L, new BigDecimal("100.50"), "user1@mail.net");
    User third = new User(1L, new BigDecimal("100.50"), "user1@mail.net");
    User other = new User(2L, new BigDecimal("100.50"), "user2@mail.net");





    @Test
    void equals_is_reflexive() {
        Assertions.assertEquals(first, same);
    }

    @Test
    void equals_is_symmetric() {
        Assertions.assertEquals(first, second);
        Assertions.assertEquals(second, first);
    }

    @Test
    void equals_is_transitive() {
        Assertions.assertEquals(first,second);
        Assertions.assertEquals(second,third);
        Assertions.assertEquals(third, first);

    }
    @Test
    void equals_returns_false_when_null() {
        Assertions.assertNotEquals(first, null);
    }

    @Test
    void equals_returns_false_for_different_type() {
        Assertions.assertNotEquals(first, "not a user");
    }

    @Test
    void equal_objects_have_same_hashcode() {
        Assertions.assertEquals(first,second);
        Assertions.assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void users_with_different_ids_are_not_equal() {
        Assertions.assertNotEquals(first, other);
    }

    @Test
    void changing_state_does_not_affect_equality() {
        User modified = new User(1L, new BigDecimal("100.50"), "user1@mail.net");

        modified.setEmail("new@mail.net");
        modified.setMoney(new BigDecimal("999.99"));

        Assertions.assertEquals(first, modified);
    }
}
