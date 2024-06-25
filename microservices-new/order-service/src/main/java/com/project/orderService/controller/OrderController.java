package com.project.orderService.controller;

import com.project.orderService.dto.OrderDto;
import com.project.orderService.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String placeRequest(@RequestBody OrderDto orderRequest){
    orderService.createOrder(orderRequest);
    return "Created" ;
    }


}
