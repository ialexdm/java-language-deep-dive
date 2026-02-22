package com.ivashinin.deepdive.collections;

class Node<K,V> {
    private final K key;
    private V value;

    Node<K, V> getNext() {
        return next;
    }

    void setNext(Node<K, V> next) {
        this.next = next;
    }

    private Node<K,V> next;

    public Node(K key, V value) {
        if (key == null)
        {
            throw new NullPointerException("Key should not be null");
        }
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }


    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
