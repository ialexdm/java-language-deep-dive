package com.ivashinin.deepdive.streams;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Tasks {
    public static void main(String[] args) {
        /*Task 1. Получить список пользователей без повторяющихся email
        оставить первого встретившегося)
        */
        System.out.println("Task 1.");
        Stream<User> users = Stream.<User>builder()
                .add(new User(1L, "1@mail.ru"))
                .add(new User(2L, "2@mail.ru"))
                .add(new User(3L, "1@mail.ru"))
                .add(new User(4L, "2@mail.ru"))
                .build();

        System.out.println("Users with unique emails:");
        List<User> uniqueUsers = users
                .collect(
                        toMap(
                                User::getEmail,
                                u -> u,
                                (first, duplicate) -> first,
                                LinkedHashMap::new))
                        .values().stream().toList();
        uniqueUsers.forEach(System.out::println);

        //Task 2. Посчитать сумму по каждой категории.
        System.out.println("Task 2.");

        Stream<Transaction> transactions = Stream.<Transaction>builder()
                .add(new Transaction("taxes", new BigDecimal("300.00")))
                .add(new Transaction("sales", new BigDecimal("200.00")))
                .add(new Transaction("credit", new BigDecimal("200.00")))
                .add(new Transaction("credit", new BigDecimal("400.00")))
                .build();

        Map<String, BigDecimal> result = transactions.collect(toMap(
                Transaction::getCategory, Transaction::getAmount, BigDecimal::add));
        System.out.println("Amounts grouped by category:");
        System.out.println(result);

        /*Task 3. Есть список заказов.
        Проверить, что нет ни одного заказа с отрицательной суммой.
        Вернуть boolean. */
        System.out.println("Task 3.");

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1l, new BigDecimal("10.00")));
        orderList.add(new Order(1l, new BigDecimal("20.00")));
        orderList.add(new Order(1l, new BigDecimal("-30.00")));
        orderList.add(new Order(1l, new BigDecimal("10.00")));

        boolean hasOnlyPositiveAmounts = !orderList
                .stream()
                .anyMatch(o -> o
                        .getAmount()
                        .compareTo(BigDecimal.ZERO) < 0);
        System.out.println("Has orderList all orders more than zero?");
        System.out.println(hasOnlyPositiveAmounts);

        //Task 4. Найти топ-3 по продажам.
        System.out.println("Task 4.");

        Stream<Product> products = Stream.<Product>builder()
                .add(new Product("Milk", 5))
                .add(new Product("Eggs", 3))
                .add(new Product("Meat", 5))
                .add(new Product("Cake", 4))
                .build();

        List<Product> result3 = products.sorted(Comparator.comparing(Product::getSales).reversed()).limit(3).toList();
        System.out.println("Top 3 the most salable products:");
        for (Product product : result3){
            System.out.println(product.getName());
        }

        //Task 5. Найти самый часто продаваемый товар.
        System.out.println("Task 5.");

        Stream<FinishedOrder> finishedOrders = Stream.<FinishedOrder>builder()
                .add(new FinishedOrder(
                        new Item("Milk", 3),
                        new Item("Meat",2)))
                .add(new FinishedOrder(
                        new Item("Milk", 1),
                        new Item("Meat",1),
                        new Item("Cake", 1)))
                .add(new FinishedOrder(
                        new Item("Meat", 5),
                        new Item("Tomato", 2)
                ))
                .build();
        String mostFrequentItem = finishedOrders.flatMap(
                order -> order.getItems().stream())
                .collect(toMap(Item::getName,Item::getQuantity, Integer::sum))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
        System.out.println("The most frequent item in order is:");
        System.out.println(mostFrequentItem);
    }

    //inner classes for task

    //class name order reserved by another class
    private static class FinishedOrder {
        List<Item> items;

        FinishedOrder(Item... i) {
            this.items = Arrays.asList(i);
        }

        List<Item> getItems() {
            return List.copyOf(items);
        }
    }
    private static class Item {
        String name;
        int quantity;

        Item(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        String getName() {
            return name;
        }

        int getQuantity() {
            return quantity;
        }
    }
    private static class Product {
        String name;
        int sales;

        Product(String name, int sales) {
            this.name = name;
            this.sales = sales;
        }

        String getName() {
            return name;
        }

        int getSales() {
            return sales;
        }
    }


    private static class Order{
        Long id;

        BigDecimal getAmount() {
            return amount;
        }

        BigDecimal amount;

        Order(long id, BigDecimal amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    private static class Transaction {
        String category;
        BigDecimal amount;
        Transaction(String category, BigDecimal amount){
            this.category = category;
            this.amount = amount;
        }

        String getCategory() {
            return category;
        }

        BigDecimal getAmount() {
            return amount;
        }
    }
    private static class User {
        Long id;
        String email;

        User(Long id, String email) {
            this.id = id;
            this.email = email;
        }

        String getEmail(){
            return  this.email;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;
            return Objects.equals(email, user.email);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(email);
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
