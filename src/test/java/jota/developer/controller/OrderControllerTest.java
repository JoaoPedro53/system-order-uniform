package jota.developer.controller;

import jota.developer.domain.Client;
import jota.developer.domain.Order;
import jota.developer.domain.School;
import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import jota.developer.repository.OrderRepository;
import jota.developer.repository.OrderRepositoryData;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = OrderController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "jota.developer")
//@Import({OrderMapperImpl.class, OrderService.class, OrderRepository.class, OrderRepositoryData.class})
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private OrderRepositoryData repositoryData;
    @MockitoSpyBean
    private OrderRepository repository;
    private final List<Order> ordersList = new ArrayList<>();
    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var dateTimePurchase = localDateTimePurchase();

        var order1 = Order.builder().orderId(1L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(dateTimePurchase)
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order2 = Order.builder().orderId(2L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(dateTimePurchase)
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("Pedro", "82 99760-2347")).build();
        var order3 = Order.builder().orderId(3L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(dateTimePurchase)
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("Alfredo", "82 99760-2347")).build();
        ordersList.addAll(List.of(order1, order2, order3));
    }

    @Test
    @DisplayName("GET v1/orders return all orders list when argument name is null")
    @org.junit.jupiter.api.Order(1)
    void listAllOrdersOrOrdersByNameClient_ReturnAllOrders_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var response = readResourceFile("order/get-order-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders?name=João return orders list by name client when argument name is give and is found")
    @org.junit.jupiter.api.Order(2)
    void listAllOrdersOrOrdersByNameClient_ReturnOrdersByNameClient_WhenArgumentNameIsGive() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var response = readResourceFile("order/get-order-by-name-joao-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders").param("name", "João"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders?name=x return orders empty list when client name give is not found")
    @org.junit.jupiter.api.Order(3)
    void listAllOrdersOrOrdersByNameClient_ReturnOrdersEmptyList_WhenArgumentClientNameGiveIsNotFound() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var response = readResourceFile("order/get-order-by-name-x-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders").param("name", "x"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders/1 return order with give id")
    @org.junit.jupiter.api.Order(4)
    void findById_ReturnOrderById_WhenSuccessful() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var response = readResourceFile("order/get-order-by-id-1-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/orders/100 throw ResponseStatusException 404 when order is not found")
    @org.junit.jupiter.api.Order(5)
    void findById_ThrowResponseStatusException_WhenOrderIsNotFound() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/100"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST v1/orders creates a order")
    @org.junit.jupiter.api.Order(6)
    void save_CreatesOrder_WhenSuccessful() throws Exception {
        var request = readResourceFile("order/post-request-order-200.json");
        var response = readResourceFile("order/post-response-order-201.json");
        var dateTimePurchase = localDateTimePurchase();

        var orderToSave = Order.builder().orderId(1L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(dateTimePurchase)
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(orderToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/orders")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

    private LocalDateTime localDateTimePurchase() {
        var dateTime = "2026-04-17T11:01:01.3905248";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        return LocalDateTime.parse(dateTime, formatter);
    }

}