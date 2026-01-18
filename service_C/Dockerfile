# Use a slim Python image for the smallest possible footprint
FROM python:3.11-slim


# Set the working directory inside the container
WORKDIR /app

# 1. Copy requirements first to leverage Docker's layer caching
COPY service_C/requirements.txt .

# 2. Install only the runtime dependencies
# Note: You still need 'grpcio' and 'protobuf' in your requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# 3. Copy your local service-C folder contents
# This includes your main.py and your pre-generated _pb2.py files
COPY service_C/ .

# Force python to flush logs to stdout/stderr immediately
ENV PYTHONUNBUFFERED=1
ENV PYTHONPATH=/app

# Expose the gRPC port
EXPOSE 50051

# 4. Run the server from its specific location
# Note the path: app/inventory_server.py
CMD ["python", "app/inventory_server.py"]