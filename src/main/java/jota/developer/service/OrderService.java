package jota.developer.service;

import jota.developer.domain.Order;
import jota.developer.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

public class OrderService {
    private OrderRepository repository;

    public OrderService() {
        this.repository = new OrderRepository();
    }

    public List<Order> listAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public List<Order> searchByDate(LocalDate date) {
        return repository.searchByDate(date);
    }

    public Order findByIdOrThrowNotFound(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not Found"));
    }

    public Order save(Order order){
        return repository.save(order);
    }

    public void delete(Long id){
        var order = findByIdOrThrowNotFound(id);
        repository.delete(order);
    }
    public void update(Order orderToUpdate){
        var order = findByIdOrThrowNotFound(orderToUpdate.getOrderId());
        orderToUpdate.setPurchaseDate(order.getPurchaseDate());
        orderToUpdate.setDeliveryDate(order.getDeliveryDate());
        repository.update(orderToUpdate);
    }
}
