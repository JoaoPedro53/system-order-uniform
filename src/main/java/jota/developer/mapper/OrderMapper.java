package jota.developer.mapper;

import jota.developer.domain.Order;
import jota.developer.request.OrderPostRequest;
import jota.developer.request.OrderPutRequest;
import jota.developer.response.OrderGetResponse;
import jota.developer.response.OrderPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderId", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    @Mapping(target = "purchaseDate", expression = "java(java.time.LocalDate.now())")
    Order toOrder(OrderPostRequest orderPostRequest);

    Order toOrder(OrderPutRequest orderPutRequest);

    List<OrderGetResponse> toListOrderGetResponse(List<Order> orders);
    OrderGetResponse toOrderGetResponse(Order order);

    OrderPostResponse toOrderPostResponse(Order order);
}
