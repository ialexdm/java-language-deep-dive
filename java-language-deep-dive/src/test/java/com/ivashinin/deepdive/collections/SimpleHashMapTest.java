package com.ivashinin.deepdive.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleHashMapTest {


    @Test
    public void put_withNullKey_throwsNPE(){
        SimpleHashMap<Object, Object> testMap= new SimpleHashMap<>();
        Assertions.assertThrows(NullPointerException.class, () -> testMap.put(null, new Object()));
    }

    @Test
    public void putAndGet_shouldWork(){
        SimpleHashMap<String, Integer> testMap= new SimpleHashMap<>();
        testMap.put("a",1);
        Assertions.assertEquals(1, testMap.get("a"));
        Assertions.assertEquals(1, testMap.size());
    }
    @Test
    public void put_withContainedKey_shouldReplaceValue(){
        SimpleHashMap<String, Integer> testMap= new SimpleHashMap<>();
        testMap.put("a",1);
        testMap.put("a",2);
        Assertions.assertEquals(2, testMap.get("a"));
        Assertions.assertEquals(1, testMap.size());
    }

    @Test
    public void put_differentKeyWithSameHashcode_shouldAddAll(){
        class BadKey {
            private final String value;

            BadKey(String value) {
                this.value = value;
            }

            @Override
            public int hashCode() {
                return 1;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof BadKey)) return false;
                BadKey other = (BadKey) o;
                return value.equals(other.value);
            }
        }
        SimpleHashMap<BadKey, Integer> testMap= new SimpleHashMap<>();
        BadKey first = new BadKey("a");
        BadKey second = new BadKey("b");
        testMap.put(first,1);
        testMap.put(second,2);
        Assertions.assertEquals(1, testMap.get(first));
        Assertions.assertEquals(2, testMap.get(second));
        Assertions.assertEquals(2, testMap.size());
    }

    @Test
    public void putAndGet_shouldWorkAfterResize(){
        SimpleHashMap<String, Integer> testMap= new SimpleHashMap<>();
        for (int i = 0; i < 33; i++) {
            testMap.put("a" +i, i);
        }
        Assertions.assertEquals(33, testMap.size());

        for (int i = 0; i < 33; i++) {
            Assertions.assertEquals(i, testMap.get("a"+i));
        }
    }
}
