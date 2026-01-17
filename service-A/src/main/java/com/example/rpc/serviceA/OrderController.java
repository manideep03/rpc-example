package com.example.rpc.serviceA;

import com.example.rpc.order.OrderRequest;
import com.example.rpc.order.OrderResponse;
import com.example.rpc.order.OrderServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class OrderController {

    @GrpcClient("order-service") // Matches the property name above
    private OrderServiceGrpc.OrderServiceBlockingStub orderStub;

    @GetMapping("/order")
    public String createOrder(@RequestParam String id) {
        OrderRequest request = OrderRequest.newBuilder()
                .setItemId(id)
                .setPrice(1)
                .build();

        OrderResponse response = orderStub
                // timeout to call
                .withDeadlineAfter(2, TimeUnit.SECONDS)
                .placeOrder(request);
        return "Order Status: " + response.getStatus() + " ID: " + response.getOrderId();
    }
}