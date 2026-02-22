package com.ivashinin.deepdive.collections;


public class SimpleHashMap<K, V> {
    private Node<K,V>[] entrySet;
    private double loadFactor;
    private int size;

    public SimpleHashMap() {
        this.entrySet = new Node[8];
        this.loadFactor = 0.75;
        this.size = 0;
    }

    public void put(K key, V value) {
        if (key == null){
            throw new NullPointerException("Key should not be null");
        }

        int index = index(key);

        if(entrySet[index] == null)
        {
            entrySet[index] = new Node<>(key,value);
            size++;
            resize();

            return;
        }else if (entrySet[index].getKey().equals(key))
        {
            entrySet[index].setValue(value);
            return;
        }

        Node<K,V> current = entrySet[index];
        while(current != null)
        {
            if (current.getNext() == null){
                current.setNext(new Node<>(key, value));
                current = null;
                size++;
                resize();

            } else if (current.getNext().getKey().equals(key)) {
                current.getNext().setValue(value);
                current = null;
            }else{
                current = current.getNext();
            }
        }
    }

    private void resize() {
        if (size > loadFactor * entrySet.length) {
            Node<K, V>[] oldEntrySet = entrySet;
            this.entrySet = new Node[oldEntrySet.length * 2];

            for (int i = 0; i < oldEntrySet.length; i++) {
                if (oldEntrySet[i] != null) {
                    Node<K, V> current = oldEntrySet[i];
                    while (current != null) {
                        reHashNode(current, index(current.getKey()));
                        Node<K, V> next = current.getNext();
                        current.setNext(null);
                        current = next;
                    }
                }
            }
        }
    }

    private void reHashNode(Node<K,V> node, int index)
    {
        if (this.entrySet[index] == null)
        {
            this.entrySet[index] = node;
        }else {
            Node<K,V> current = this.entrySet[index];
            while(current.getNext() != null)
            {
                current = current.getNext();
            }
            current.setNext(node);
        }
    }

    public V get(K key) {
        if (key == null)
        {
            throw new NullPointerException("Null keys are not supported");
        }
        int index = index(key);
        if (entrySet[index] == null)
        {
            return null;
        }
        if (entrySet[index].getKey().equals(key))
        {
            return entrySet[index].getValue();
        }
        Node<K,V> current =  entrySet[index].getNext();
        while (current != null)
        {
            if (current.getKey().equals(key)){
                return current.getValue();
            }else {
                current = current.getNext();
            }
        }
        return current == null ? null : current.getValue();


    }
    public int size() {
        return size;
    }

    private int index(K key){
        int hash = key.hashCode();
        hash ^= (hash >>> 16);
        return hash & (entrySet.length - 1);
    }

}