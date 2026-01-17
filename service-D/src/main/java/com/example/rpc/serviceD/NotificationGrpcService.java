package com.example.rpc.serviceD;

import com.example.rpc.notification.NotificationRequest;
import com.example.rpc.notification.NotificationServiceGrpc;
import com.example.rpc.notification.NotificationUpdate;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class NotificationGrpcService extends NotificationServiceGrpc.NotificationServiceImplBase {
    @Override
    public void subscribeToUpdates(NotificationRequest request, StreamObserver<NotificationUpdate> responseObserver) {
        String[] statuses = {"Order Placed", "Payment Successful", "Inventory Reserved", "Shipped"};

        for (String s : statuses) {
            NotificationUpdate update = NotificationUpdate.newBuilder()
                    .setStatus(s)
                    .setTimestamp(java.time.Instant.now().toString())
                    .build();

            responseObserver.onNext(update); // Push an update

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            } // Simulate delay
        }

        responseObserver.onCompleted(); // Close the stream
    }
}
