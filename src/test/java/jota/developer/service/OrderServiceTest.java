package jota.developer.service;

import jota.developer.commons.LocalDateTimePurchaseUtills;
import jota.developer.commons.OrderUtills;
import jota.developer.domain.Order;
import jota.developer.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService service;
    @Mock
    private OrderRepository repository;
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
    @org.junit.jupiter.api.Order(1)
    @DisplayName("findAll return list with all orders when successful")
    void findAll_ReturnListWithAllOrders_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(ordersList);

        var emptyOrderList = service.listAll(null);
        Assertions.assertThat(emptyOrderList).hasSameElementsAs(ordersList);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("findAll return list orders by give client name")
    void findAll_ReturnListWithOrdersByGiveName_WhenSuccessful() {
        var orderExpected = ordersList.getFirst();
        BDDMockito.when(repository.findByName(orderExpected.getClient().getName())).thenReturn(List.of(orderExpected));

        var emptyOrdersList = service.listAll(orderExpected.getClient().getName());
        org.assertj.core.api.Assertions.assertThat(emptyOrdersList).contains(orderExpected);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("findAll return empty list orders when client name give not found")
    void findAll_ReturnEmptyListWhenClientNameGiveNotFound_WhenSuccessful() {
        BDDMockito.when(repository.findByName("x")).thenReturn(List.of());

        var emptyStudentsList = service.listAll("x");
        Assertions.assertThat(emptyStudentsList).isEmpty();
    }

    @Test
    @DisplayName("findById return order with give id")
    @org.junit.jupiter.api.Order(4)
    void findById_ReturnOrderById_WhenSuccessful() {
        var order = ordersList.getFirst();
        BDDMockito.when(repository.findById(order.getOrderId())).thenReturn(Optional.of(order));

        var producerById = service.findByIdOrThrowNotFound(order.getOrderId());
        Assertions.assertThat(producerById).isEqualTo(order);
    }

    @Test
    @DisplayName("findById throw ResponseStatusException when order is not found")
    @org.junit.jupiter.api.Order(5)
    void findById_ThrowResponseStatusException_WhenOrderIsNotFound() {
        var order = ordersList.getFirst();
        BDDMockito.when(repository.findById(order.getOrderId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(order.getOrderId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save order is transfer correct for repository to be saved")
    @org.junit.jupiter.api.Order(6)
    void save_TransferOrderForRepositoryToBeSaved_WhenSuccessful() {
        var order = orderUtills.newOrderToSave();

        BDDMockito.when(repository.save(order)).thenReturn(order);
        var producerToSave = service.save(order);

        Assertions.assertThat(producerToSave).isEqualTo(order);
        BDDMockito.then(repository).should().save(order);
    }

    @Test
    @DisplayName("delete removes order")
    @org.junit.jupiter.api.Order(7)
    void delete_RemoveOrder_WhenSuccessful() {
        var orderToDelete = ordersList.getFirst();

        BDDMockito.when(repository.findById(orderToDelete.getOrderId())).thenReturn(Optional.of(orderToDelete));
        BDDMockito.doNothing().when(repository).delete(orderToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(orderToDelete.getOrderId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when order is not found ")
    @org.junit.jupiter.api.Order(8)
    void delete_ThrowsResponseStatusException_WhenOrderIsNotFound() {
        var orderToDelete = ordersList.getFirst();
        BDDMockito.when(repository.findById(orderToDelete.getOrderId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(orderToDelete.getOrderId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a order")
    @org.junit.jupiter.api.Order(9)
    void update_updatedOrder_WhenSuccessful() {
        var orderToUpdate = ordersList.getFirst();
        orderToUpdate.setDetails("Mais x dedos no lado direito");

        BDDMockito.when(repository.findById(orderToUpdate.getOrderId())).thenReturn(Optional.of(orderToUpdate));
        BDDMockito.doNothing().when(repository).update(orderToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(orderToUpdate));
    }

    @Test
    @DisplayName("update throw ResponseStatusException when order is not found")
    @org.junit.jupiter.api.Order(10)
    void update_ThrowNotFound_WhenOrderIsNotFound() {
        var orderToUpdate = ordersList.getFirst();
        BDDMockito.when(repository.findById(orderToUpdate.getOrderId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(orderToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("searchByDeliveryDate return order list with give date")
    @org.junit.jupiter.api.Order(11)
    void searchByDeliveryDate_ReturnOrderListWithGiveDate_whenSuccessful() {
        var orderByDeliveryDateExpected = ordersList.getFirst();
        BDDMockito.when(repository.searchByDeliveryDate(orderByDeliveryDateExpected.getDeliveryDate())).thenReturn(List.of(orderByDeliveryDateExpected));

        var ordersByDate = service.searchByDeliveryDate(orderByDeliveryDateExpected.getDeliveryDate());
        Assertions.assertThat(ordersByDate).contains(orderByDeliveryDateExpected);

    }

    @Test
    @DisplayName("searchByDeliveryDate return empty order list when don't have orders for give date")
    @org.junit.jupiter.api.Order(12)
    void searchByDeliveryDate_ReturnEmptyOrderListWithGiveDate_whenSuccessful() {
        var orderByDeliveryDateExpected = ordersList.getFirst();
        BDDMockito.when(repository.searchByDeliveryDate(orderByDeliveryDateExpected.getDeliveryDate())).thenReturn(List.of());

        var ordersByDate = service.searchByDeliveryDate(orderByDeliveryDateExpected.getDeliveryDate());
        Assertions.assertThat(ordersByDate).isEmpty();

    }
}