package com.project.orderService.service;

import com.project.orderService.dto.InventoryResponse;
import com.project.orderService.dto.OrderDto;
import com.project.orderService.dto.OrderLineItemsDto;

import com.project.orderService.model.Order;
import com.project.orderService.model.OrderLineItems;
import com.project.orderService.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
   private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    public OrderService(OrderRepository orderRepository,WebClient.Builder webClient) {
        this.orderRepository = orderRepository;
        this.webClient= webClient;
    }
    public void createOrder(OrderDto orderRequest){
       Order order =new Order();
       order.setOrderNumber(UUID.randomUUID().toString());


       List<OrderLineItems> orderLineItemsLists= orderRequest.getOrderLineItemsLists()
                                                 .stream().map(this::maptodto).toList();

       order.setOrderLineItemsLists(orderLineItemsLists);
        List<String> skuCodes = order.getOrderLineItemsLists().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        System.out.println("Order->service->createOrder");

     InventoryResponse[] inventoryResponses= webClient.build().get().uri("http://inventory-service/api/inventory",uriBuilder ->uriBuilder.queryParam("skuCode",skuCodes).build())
                                                 .retrieve()
                                                         .bodyToMono(InventoryResponse[].class)
                                                                 .block();
     boolean allProductsInStock= Arrays.stream(inventoryResponses).allMatch(inventoryResponse -> inventoryResponse.isInStock());
        if (allProductsInStock) {
            orderRepository.save(order);
            System.out.println("Printed");

         return;
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }


    }

    private OrderLineItems maptodto(OrderLineItemsDto orderRequestLineItems) {
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderRequestLineItems.getPrice());
        orderLineItems.setQuantity(orderLineItems.getQuantity());
        orderLineItems.setSkuCode(orderLineItems.getSkuCode());
        return orderLineItems;
    }
}
