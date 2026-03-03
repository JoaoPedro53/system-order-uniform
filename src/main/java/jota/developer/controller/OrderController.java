package jota.developer.controller;

import jota.developer.domain.Order;
import jota.developer.mapper.OrderMapper;
import jota.developer.request.OrderPostRequest;
import jota.developer.request.OrderPutRequest;
import jota.developer.response.OrderGetResponse;
import jota.developer.response.OrderPostResponse;
import jota.developer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/orders")
public class OrderController {
    private static final OrderMapper MAPPER = OrderMapper.INSTANCE;
    private OrderService service;

    public OrderController(){
        this.service = new OrderService();
    }

    @GetMapping
    public ResponseEntity<List<OrderGetResponse>> listAllOrdersOrOrdersByNameClient(@RequestParam(required = false) String name){
        log.info("request received to list all orders, param name: '{}'", name);

        var orders = service.listAll(name);
        var orderGetResponse = MAPPER.toListOrderGetResponse(orders);
        return ResponseEntity.ok(orderGetResponse);

    }

    @GetMapping("{id}")
    public ResponseEntity<OrderGetResponse> findById(@PathVariable Long id){
        log.info("request received to list order by id: '{}'", id);

        var order = service.findByIdOrThrowNotFound(id);
        var orderGetResponse = MAPPER.toOrderGetResponse(order);
        return ResponseEntity.ok(orderGetResponse);

    }

    @GetMapping("/searchByDate")
    public ResponseEntity<List<OrderGetResponse>> searchByDate(@RequestParam LocalDate date){
        log.info("request received to list order by date: '{}'", date);

         var ordersByDate = service.searchByDate(date);
         var ordersByDateGetResponse = MAPPER.toListOrderGetResponse(ordersByDate);
        return ResponseEntity.ok(ordersByDateGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderPostResponse> save(@RequestBody OrderPostRequest orderPostRequest) {
        log.info("request received to create order: '{}'", orderPostRequest);

        var order = MAPPER.toOrder(orderPostRequest);
        var orderSaved = service.save(order);
        var orderPostResponse = MAPPER.toOrderPostResponse(orderSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        log.info("request received to delete order: '{}'", id);

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody OrderPutRequest orderPutRequest){
        log.info("request received to update order: '{}'", orderPutRequest);

        var orderUpdate = MAPPER.toOrder(orderPutRequest);
        service.update(orderUpdate);

        return ResponseEntity.noContent().build();
    }

}
