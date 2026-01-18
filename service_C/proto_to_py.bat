:: proto_to_py.bat
:: Description: batch script to create rpc base files for python

@echo off
echo Starting script

python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. inventory.proto

echo Script finished.