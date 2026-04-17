package jota.developer.repository;

import jota.developer.domain.Client;
import jota.developer.domain.Order;
import jota.developer.domain.School;
import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderRepositoryTest {
    @InjectMocks
    public OrderRepository repository;
    @Mock
    private OrderRepositoryData repositoryData;
    private final List<Order> ordersList = new ArrayList<>();

    @BeforeEach
    void init(){
        var order1 = Order.builder().orderId(10L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order2 = Order.builder().orderId(2L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        ordersList.addAll(List.of(order1, order2));
    }

    @Test
    @DisplayName("findAll return all orders when successful")
    @org.junit.jupiter.api.Order(1)
    void findAll_ReturnAllOrders_WhenSuccessful(){
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orders = repository.findAll();
        Assertions.assertThat(orders).hasSameElementsAs(ordersList);
    }

    @Test
    @DisplayName("findByName return order by name when successful")
    @org.junit.jupiter.api.Order(2)
    void findByName_ReturnOrderByName_WhenSuccessful(){
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orders = repository.findByName(null);
        Assertions.assertThat(orders).isEmpty();
    }

    @Test
    @DisplayName("findById return order by id when successful")
    @org.junit.jupiter.api.Order(3)
    void findById_ReturnOrderById_WhenSuccessful(){
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderExpected = ordersList.getFirst();
        var order = repository.findById(orderExpected.getOrderId());
        Assertions.assertThat(order).contains(orderExpected);
    }

    @Test
    @DisplayName("searchByDate return order by delivery date when successful")
    @org.junit.jupiter.api.Order(4)
    void searchByDate_ReturnOrderByDeliveryDate_WhenSuccessful(){
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderExpectedForDate = ordersList.getFirst();
        var ordersByDate = repository.searchByDate(LocalDate.of(2026, 03, 20));

        Assertions.assertThat(ordersByDate).contains(orderExpectedForDate);
    }

    @Test
    @DisplayName("save saved the order")
    @org.junit.jupiter.api.Order(5)
    void save_SavedOrder_WhenSuccessful(){
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var order = Order.builder().orderId(2L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        repository.save(order);
        Assertions.assertThat(this.ordersList).contains(order);
    }

    @Test
    @DisplayName("update updated the order")
    @org.junit.jupiter.api.Order(6)
    void update_UpdatedOrder_WhenSuccessful(){
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderToUpdate = ordersList.getFirst();
        orderToUpdate.setUniformSizeUp(UniformSizeUp.PP);
        repository.update(orderToUpdate);

        Assertions.assertThat(this.ordersList).isNotNull().contains(orderToUpdate);

        var order = repository.findById(orderToUpdate.getOrderId());
        Assertions.assertThat(order).isPresent();
        Assertions.assertThat(order.get().getUniformSizeUp()).isEqualTo(UniformSizeUp.PP);


    }

    @Test
    @DisplayName("delete remove the order")
    @org.junit.jupiter.api.Order(7)
    void delete_DeletedOrder_WhenSuccessful(){
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderToDelete = ordersList.getFirst();
        repository.delete(orderToDelete);

        Assertions.assertThat(this.ordersList).doesNotContain(orderToDelete);


    }

}