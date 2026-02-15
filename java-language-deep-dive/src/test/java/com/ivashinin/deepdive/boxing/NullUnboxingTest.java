package com.ivashinin.deepdive.boxing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NullUnboxingTest {

    @Test
    public void shouldThrowNullPointerExceptionWhenUnboxingNull(){
        Integer a = null;

        assertThrows(
                NullPointerException.class, () -> {
                    int b = a;
                }
        );
    }

    @Test
    public void shouldSetZeroIfWrapperIsNull(){
        Integer a = null;

        int b = Optional.ofNullable(a).orElse(0);
        Assertions.assertEquals(0,b);
    }

}
