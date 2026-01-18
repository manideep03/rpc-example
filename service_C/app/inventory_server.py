import grpc
from concurrent import futures

from protos import inventory_pb2
from protos import inventory_pb2_grpc

print("start C")
# This class "Implements" the interface defined in our proto file
class InventoryServicer(inventory_pb2_grpc.InventoryServiceServicer):

    def CheckStock(self, request, context):
        print(f"Received request for Product: {request.product_id}")

        # Simple logic: If product starts with 'A', it's in stock
        is_available = request.product_id.startswith("A")

        return inventory_pb2.InventoryResponse(
            is_available=is_available,
            message="Stock check completed successfully"
        )

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    inventory_pb2_grpc.add_InventoryServiceServicer_to_server(InventoryServicer(), server)
    server.add_insecure_port('[::]:50051')
    print("Inventory Service (Python - Service C) started on port 50051...")
    server.start()
    server.wait_for_termination()

if __name__ == '__main__':
    print("start main!")
    serve()