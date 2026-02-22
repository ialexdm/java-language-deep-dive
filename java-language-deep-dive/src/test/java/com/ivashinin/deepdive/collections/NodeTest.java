package com.ivashinin.deepdive.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeTest {

    @Test
    public void constructor_keyIsNull_throwsException(){
        Assertions.assertThrows(NullPointerException.class, () -> new Node<Object, Object>(null, new Object()));
    }

}
