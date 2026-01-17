package com.example.rpc.serviceB;

import com.example.rpc.order.OrderRequest;
import com.example.rpc.order.OrderResponse;
import com.example.rpc.order.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class OrderGrpcServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {
    @Override
    public void placeOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        log.info("Service A called me to place an order for: {}", request.getItemId());

        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId("ORD-999")
                .setStatus("SUCCESS")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
