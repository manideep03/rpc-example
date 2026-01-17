package com.example.rpc.serviceB;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class GrpcClientTest implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting gRPC Call to Python Service...");

        // 1. Create the communication channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // Necessary because our Python server isn't using SSL
                .build();

        // 2. Create a blocking stub (synchronous)
        com.example.rpc.inventory.InventoryServiceGrpc.InventoryServiceBlockingStub stub =
                com.example.rpc.inventory.InventoryServiceGrpc.newBlockingStub(channel);

        // 3. Prepare the request
        com.example.rpc.inventory.InventoryRequest request = com.example.rpc.inventory.InventoryRequest.newBuilder()
                .setProductId("ABC-123")
                .setQuantity(5)
                .build();

        // 4. Make the call
        try {
            com.example.rpc.inventory.InventoryResponse response = stub.checkStock(request);
            System.out.println(">>> Response from Python: " + response.getMessage());
            System.out.println(">>> Is Available: " + response.getIsAvailable());
        } catch (Exception e) {
            System.err.println("RPC Call Failed: " + e.getMessage());
        }

        channel.shutdown();
    }
}