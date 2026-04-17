package jota.developer.controller;

import jota.developer.domain.Client;
import jota.developer.domain.Order;
import jota.developer.domain.School;
import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import jota.developer.mapper.OrderMapperImpl;
import jota.developer.repository.OrderRepository;
import jota.developer.repository.OrderRepositoryData;
import jota.developer.service.OrderService;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
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
    private final List<Order> ordersList = new ArrayList<>();
    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var dateTime = "2026-04-17T11:01:01.3905248";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var dateTimeFormated = LocalDateTime.parse(dateTime, formatter);

        var order1 = Order.builder().orderId(1L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(dateTimeFormated)
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order2 = Order.builder().orderId(2L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(dateTimeFormated)
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order3 = Order.builder().orderId(3L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(dateTimeFormated)
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        ordersList.addAll(List.of(order1, order2, order3));
    }

    @Test
    @DisplayName("listAllOrdersOrOrdersByNameClient return all orders when argument name is null")
    @org.junit.jupiter.api.Order(1)
    void listAllOrdersOrOrdersByNameClient_ReturnAllOrders_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(repositoryData.getORDERS()).thenReturn(ordersList);

        var response = readResourceFile("order/get-order-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

}