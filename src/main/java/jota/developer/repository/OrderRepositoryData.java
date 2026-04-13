package jota.developer.repository;

import jota.developer.domain.Client;
import jota.developer.domain.Order;
import jota.developer.domain.School;
import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepositoryData {
    private final List<Order> ORDERS = new ArrayList<>();

    {
        var order1 = Order.builder().orderId(10L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order2 = Order.builder().orderId(2L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order3 = Order.builder().orderId(9L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        ORDERS.addAll(List.of(order1, order2, order3));
    }

    public List<Order> getORDERS() {
        return ORDERS;
    }
}