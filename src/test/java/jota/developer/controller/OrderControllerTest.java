package jota.developer.controller;

import jota.developer.commons.FileUtills;
import jota.developer.commons.OrderUtills;
import jota.developer.domain.Order;
import jota.developer.repository.OrderRepository;
import jota.developer.repository.OrderRepositoryData;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = OrderController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"jota.developer"})
//@ActiveProfiles("test")
//@Import({OrderMapperImpl.class, OrderService.class, OrderRepository.class, OrderRepositoryData.class})
class OrderControllerTest {
    private static final String URL = "/v1/orders";

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private OrderRepositoryData repositoryData;
    @MockitoSpyBean
    private OrderRepository repository;
    private List<Order> ordersList;
    @Autowired
    private FileUtills fileUtills;
    @Autowired
    private OrderUtills orderUtills;

    @BeforeEach
    void init() {
        ordersList = orderUtills.newListOrders();
    }

    @Test
    @DisplayName("GET v1/orders return all orders list when argument name is null")
    @org.junit.jupiter.api.Order(1)
    void listAllOrdersOrOrdersByNameClient_ReturnAllOrders_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var response = fileUtills.readResourceFile("order/get-order-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders?name=João return orders list by name client when argument name is give and is found")
    @org.junit.jupiter.api.Order(2)
    void listAllOrdersOrOrdersByNameClient_ReturnOrdersByNameClient_WhenArgumentNameIsGive() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var nameClient = "João";

        var response = fileUtills.readResourceFile("order/get-order-by-name-joao-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", nameClient))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders?name=x return orders empty list when client name give is not found")
    @org.junit.jupiter.api.Order(3)
    void listAllOrdersOrOrdersByNameClient_ReturnOrdersEmptyList_WhenArgumentClientNameGiveIsNotFound() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var nameClient = "x";

        var response = fileUtills.readResourceFile("order/get-order-by-name-x-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", nameClient))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders/'{id}' return order with give id")
    @org.junit.jupiter.api.Order(4)
    void findById_ReturnOrderById_WhenSuccessful() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var id = 1;

        var response = fileUtills.readResourceFile("order/get-order-by-id-1-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders/'{id}' throw ResponseStatusException 404 when order is not found")
    @org.junit.jupiter.api.Order(5)
    void findById_ThrowResponseStatusException_WhenOrderIsNotFound() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var id = 999;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Order not Found"));

    }

    @Test
    @DisplayName("GET v1/orders/byDeliveryDate/'{date}' return order list with give date")
    @org.junit.jupiter.api.Order(6)
    void searchByDeliveryDate_ReturnOrderListWithGiveDate_whenSuccessful() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var date = "2026-03-20";

        var response = fileUtills.readResourceFile("order/get-order-by-delivery-date-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/byDeliveryDate/{date}", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders/byDeliveryDate/'{date}' return empty order list when don't have orders for give date")
    @org.junit.jupiter.api.Order(7)
    void searchByDeliveryDate_ReturnEmptyOrderListWithGiveDate_whenSuccessful() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var date = "2090-10-20";

        var response = fileUtills.readResourceFile("order/get-order-by-delivery-date-empty-list-orders-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/byDeliveryDate/{date}", date))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("POST v1/orders creates a order")
    @org.junit.jupiter.api.Order(8)
    void save_CreatesOrder_WhenSuccessful() throws Exception {
        var request = fileUtills.readResourceFile("order/post-request-order-200.json");
        var response = fileUtills.readResourceFile("order/post-response-order-201.json");

        var orderToSave = orderUtills.newOrderToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(orderToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/orders updates a order")
    @org.junit.jupiter.api.Order(9)
    void update_updatedOrder_WhenSuccessful() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var request = fileUtills.readResourceFile("order/put-request-order-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/orders throw ResponseStatusException when order is not found")
    @org.junit.jupiter.api.Order(10)
    void update_ThrowNotFound_WhenOrderIsNotFound() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var request = fileUtills.readResourceFile("order/put-request-order-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Order not Found"));

    }

    @Test
    @DisplayName("DELETE v1/orders/'{id}' removes order")
    @org.junit.jupiter.api.Order(11)
    void delete_RemoveOrder_WhenSuccessful() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);
        var id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("DELETE v1/orders/'{id}' throws ResponseStatusException when order is not found ")
    @org.junit.jupiter.api.Order(12)
    void delete_ThrowsResponseStatusException_WhenOrderIsNotFound() throws Exception {
        var id = 999;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Order not Found"));
    }

}
