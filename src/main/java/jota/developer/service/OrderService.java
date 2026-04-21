package jota.developer.service;

import jota.developer.domain.Order;
import jota.developer.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public List<Order> listAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public List<Order> searchByDeliveryDate(LocalDate date) {
        return repository.searchByDeliveryDate(date);
    }

    public Order findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not Found"));
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    public void delete(Long id) {
        var order = findByIdOrThrowNotFound(id);
        repository.delete(order);
    }

    public void update(Order orderToUpdate) {
        var order = findByIdOrThrowNotFound(orderToUpdate.getOrderId());
        orderToUpdate.setPurchaseDate(order.getPurchaseDate());
        repository.update(orderToUpdate);
    }
}
