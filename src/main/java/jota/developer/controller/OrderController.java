package jota.developer.controller;

import jota.developer.mapper.OrderMapper;
import jota.developer.request.OrderPostRequest;
import jota.developer.request.OrderPutRequest;
import jota.developer.response.OrderGetResponse;
import jota.developer.response.OrderPostResponse;
import jota.developer.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderMapper mapper;
    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderGetResponse>> listAllOrdersOrOrdersByNameClient(@RequestParam(required = false) String name) {
        log.info("request received to list all orders, param name: '{}'", name);

        var orders = service.listAll(name);
        var orderGetResponse = mapper.toListOrderGetResponse(orders);
        return ResponseEntity.ok(orderGetResponse);

    }

    @GetMapping("{id}")
    public ResponseEntity<OrderGetResponse> findById(@PathVariable Long id) {
        log.info("request received to list order by id: '{}'", id);

        var order = service.findByIdOrThrowNotFound(id);
        var orderGetResponse = mapper.toOrderGetResponse(order);
        return ResponseEntity.ok(orderGetResponse);

    }

    @GetMapping("byDeliveryDate/{date}")
    public ResponseEntity<List<OrderGetResponse>> searchByDeliveryDate(@PathVariable LocalDate date) {
        log.info("request received to list order by date: '{}'", date);

        var ordersByDeliveryDate = service.searchByDeliveryDate(date);
        var ordersByDeliveryDateGetResponse = mapper.toListOrderGetResponse(ordersByDeliveryDate);
        return ResponseEntity.ok(ordersByDeliveryDateGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderPostResponse> save(@RequestBody OrderPostRequest orderPostRequest) {
        log.info("request received to create order: '{}'", orderPostRequest);

        var order = mapper.toOrder(orderPostRequest);
        var orderSaved = service.save(order);
        var orderPostResponse = mapper.toOrderPostResponse(orderSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("request received to delete order: '{}'", id);

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody OrderPutRequest orderPutRequest) {
        log.info("request received to update order: '{}'", orderPutRequest);

        var orderUpdate = mapper.toOrder(orderPutRequest);
        service.update(orderUpdate);

        return ResponseEntity.noContent().build();
    }

}
