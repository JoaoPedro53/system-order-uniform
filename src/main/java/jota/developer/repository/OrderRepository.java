package jota.developer.repository;

import jota.developer.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final OrderRepositoryData repositoryData;

    public List<Order> findAll() {
        return repositoryData.getORDERS();
    }

    public Optional<Order> findById(Long id) {
        return repositoryData.getORDERS().stream().filter(order -> order.getOrderId().equals(id)).findFirst();
    }

    public List<Order> findByName(String name) {
        return repositoryData.getORDERS().stream().filter(order -> order.getClient().getName().equalsIgnoreCase(name)).toList();
    }

    public List<Order> searchByDate(LocalDate date) {
        return repositoryData.getORDERS().stream().filter(order -> order.getDeliveryDate().equals(date)).toList();
    }

    public Order save(Order order) {
        repositoryData.getORDERS().add(order);
        return order;
    }

    public void delete(Order order) {
        repositoryData.getORDERS().remove(order);
    }

    public void update(Order order) {
        delete(order);
        save(order);
    }
}
