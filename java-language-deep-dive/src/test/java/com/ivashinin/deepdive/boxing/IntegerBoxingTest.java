package com.ivashinin.deepdive.boxing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class IntegerBoxingTest {

    @Test
    void shouldReuseCachedIntegerInstancesWithinDefaultCacheRange() {
        Integer a = 100;
        Integer b = 100;
        Assertions.assertSame(a, b);
    }
    @Test
    void shouldCreateNewIntegerInstancesOutsideDefaultCacheRange() {
        Integer a = 200;
        Integer b = 200;
        Assertions.assertNotSame(a, b);
    }
}
