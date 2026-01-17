### To install python rpc dependencies
```pip
pip install grpcio grpcio-tools        
```

### To convert proto files for python
```cmd
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. inventory.proto
```
Sample usage
```cmd
python -m grpc_tools.protoc -I. --python_out=inventory_service\ --grpc_python_out=inventory_service\ protos\inventory.proto 
```

### To run python service in the project
```cmd
python -m inventory_service.app.inventory_server
```
