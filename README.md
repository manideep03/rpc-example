## RPC protocol sample using a multi-language microservices flow (Java & Python).

#### The Architecture (E-commerce Order Flow)

- Service A (Spring Boot): Gateway / API Entry
- Service B (Spring Boot): Order Management
- Service C (Python FastAPI): Inventory Service
- Service D (Spring Boot): Notification Service

----

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

### Java RPC Server dependency
```xml
<dependency>
    <groupId>net.devh</groupId>
    <artifactId>grpc-server-spring-boot-starter</artifactId>
    <version>2.15.0.RELEASE</version>
</dependency>
```

### Java RPC Client dependency
```xml
<dependency>
    <groupId>net.devh</groupId>
    <artifactId>grpc-client-spring-boot-starter</artifactId>
    <version>2.15.0.RELEASE</version>
</dependency>
```

### Java proto files conversion / code generation
The process has a plugin to generate RPC dependencies and Request and Response objects as defined in .proto files
```xml
<plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <version>0.6.1</version>
    <configuration>
        <protocArtifact>com.google.protobuf:protoc:3.24.0:exe:${os.detected.classifier}</protocArtifact>
        <pluginId>grpc-java</pluginId>
        <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.58.0:exe:${os.detected.classifier}</pluginArtifact>

        <protoSourceRoot>../protos</protoSourceRoot>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>compile-custom</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
Default plugin will look for .proto files at ***src/main/proto*** if using different path configure with **protoSourceRoot** tag.

To generate code from proto compile the project. The code will be generated in target.
> TIP: To recognize these as dependencies in IntelliJ mark *target/generated-sources/grpc-java* and *target/generated-sources/java* as Generated Sources Root


----

- RPC serve which is running in Java or Python will not interfere with other Server or process
What that mean ?
    
    - I can still run my regular spring boot application on port 8080 and my RPC server on 9090. Similarly in python i can run FastAPI server and RPC server at the same time on different port.

Open Question ?
- How dose the network pool will be configured. As in Spring boot and RPC will share the network bandwidth ?