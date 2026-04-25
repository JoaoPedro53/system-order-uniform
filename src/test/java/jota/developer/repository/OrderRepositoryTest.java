package jota.developer.repository;

import jota.developer.commons.LocalDateTimePurchaseUtills;
import jota.developer.commons.OrderUtills;
import jota.developer.domain.Order;
import jota.developer.enums.UniformSizeUp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderRepositoryTest {
    @InjectMocks
    public OrderRepository repository;
    @Mock
    private OrderRepositoryData repositoryData;
    private List<Order> ordersList;
    @Mock
    private LocalDateTimePurchaseUtills localDateTimePurchaseUtills;
    @InjectMocks
    private OrderUtills orderUtills;

    @BeforeEach
    void init() {
        ordersList = orderUtills.newListOrders();
    }

    @Test
    @DisplayName("findAll return all orders when successful")
    @org.junit.jupiter.api.Order(1)
    void findAll_ReturnAllOrders_WhenSuccessful() {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orders = repository.findAll();
        Assertions.assertThat(orders).hasSameElementsAs(ordersList);
    }

    @Test
    @DisplayName("findByName return order by name when successful")
    @org.junit.jupiter.api.Order(2)
    void findByName_ReturnOrderByName_WhenSuccessful() {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orders = repository.findByName(null);
        Assertions.assertThat(orders).isEmpty();
    }

    @Test
    @DisplayName("findById return order by id when successful")
    @org.junit.jupiter.api.Order(3)
    void findById_ReturnOrderById_WhenSuccessful() {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderExpected = ordersList.getFirst();
        var order = repository.findById(orderExpected.getOrderId());
        Assertions.assertThat(order).contains(orderExpected);
    }

    @Test
    @DisplayName("searchByDeliveryDate return order by delivery date when successful")
    @org.junit.jupiter.api.Order(4)
    void searchByDeliveryDate_ReturnOrdersByDeliveryDate_WhenSuccessful() {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderExpectedForDate = ordersList.getFirst();
        var ordersByDeliveryDate = repository.searchByDeliveryDate(LocalDate.of(2026, 03, 20));

        Assertions.assertThat(ordersByDeliveryDate).contains(orderExpectedForDate);
    }

    @Test
    @DisplayName("save saved the order")
    @org.junit.jupiter.api.Order(5)
    void save_SavedOrder_WhenSuccessful() {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderToSave = orderUtills.newOrderToSave();
        repository.save(orderToSave);
        Assertions.assertThat(this.ordersList).contains(orderToSave);
    }

    @Test
    @DisplayName("update updated the order")
    @org.junit.jupiter.api.Order(6)
    void update_UpdatedOrder_WhenSuccessful() {
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
    void delete_DeletedOrder_WhenSuccessful() {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var orderToDelete = ordersList.getFirst();
        repository.delete(orderToDelete);

        Assertions.assertThat(this.ordersList).doesNotContain(orderToDelete);


    }

}