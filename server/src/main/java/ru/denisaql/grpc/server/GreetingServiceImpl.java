package ru.denisaql.grpc.server;

import io.grpc.stub.StreamObserver;
import ru.denisaql.grpc.GreetingServiceGrpc;
import ru.denisaql.grpc.HelloRequest;
import ru.denisaql.grpc.HelloResponse;

public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        System.out.println(request);

        String greeting = "Hello my dear friend, " + request.getName();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
