package jota.developer.repository;

import jota.developer.domain.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OrderRepository {
    private final static List<Order> ORDERS = new ArrayList<>();

    public List<Order> findAll() {
        return ORDERS;
    }

    public Optional<Order> findById(Long id) {
        return ORDERS.stream().filter(order -> order.getOrderId().equals(id)).findFirst();
    }

    public List<Order> findByName(String name) {
        return ORDERS.stream().filter(order -> order.getClient().getName().equalsIgnoreCase(name)).toList();
    }

    public List<Order> searchByDate(LocalDate date) {
        return ORDERS.stream().filter(order -> order.getDeliveryDate().equals(date)).toList();
    }

    public Order save(Order order){
        ORDERS.add(order);
        return order;
    }

    public void delete(Order order) {
        ORDERS.remove(order);
    }

    public void update(Order order) {
        delete(order);
        save(order);
    }
}
