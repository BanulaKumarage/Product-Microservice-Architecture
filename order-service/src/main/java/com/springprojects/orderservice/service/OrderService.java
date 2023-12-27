package com.springprojects.orderservice.service;


import com.springprojects.orderservice.dto.InventoryResponse;
import com.springprojects.orderservice.dto.OrderLinesItemsDto;
import com.springprojects.orderservice.dto.OrderRequest;
import com.springprojects.orderservice.event.OrderPlacedEvent;
import com.springprojects.orderservice.model.Order;
import com.springprojects.orderservice.model.OrderLineItems;
import com.springprojects.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLinesItemsDtos()
                .stream()
                .map(this::maptoDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkucode).toList();

        //Call Inventory Service and create order if stock available
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        Boolean allProductsInstock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInstock){
            orderRepository.save(order);
            kafkaTemplate.send("notification-topic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed successfully";
        }else{
            throw new IllegalArgumentException("Stock not available");
        }

    }

    private OrderLineItems maptoDto(OrderLinesItemsDto orderLinesItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLinesItemsDto.getPrice());
        orderLineItems.setQuantity(orderLinesItemsDto.getQuantity());
        orderLineItems.setSkucode(orderLinesItemsDto.getSkucode());
        return orderLineItems;
    }
}
