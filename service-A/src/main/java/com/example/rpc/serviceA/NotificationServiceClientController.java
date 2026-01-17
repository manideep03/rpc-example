package com.example.rpc.serviceA;

import com.example.rpc.notification.NotificationRequest;
import com.example.rpc.notification.NotificationServiceGrpc;
import com.example.rpc.notification.NotificationUpdate;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationServiceClientController {
    @GrpcClient("notification-service")
    private NotificationServiceGrpc.NotificationServiceStub asyncStub;

    @GetMapping("/noti")
    public String startListeningAsync() {
        NotificationRequest request = NotificationRequest.newBuilder().setOrderId("ODR-007").build();

        asyncStub.subscribeToUpdates(request, new StreamObserver<NotificationUpdate>() {
            @Override
            public void onNext(NotificationUpdate update) {
                // Called every time the server pushes a message
                System.out.println("ASYNC UPDATE: " + update.getStatus());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Stream failed: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                // Called when the server calls responseObserver.onCompleted()
                System.out.println("Stream finished successfully!");
            }
        });

        return "Done!";
    }
}
