package ru.denisaql.grpc.server;

import ru.denisaql.grpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class SimpleGrpcServer {
    static public void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(10080)
                .addService(new GreetingServiceImpl())
                .addService(new LevelUpServiceImpl())
                .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }

    public static class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
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

    public static class LevelUpServiceImpl extends LevelUpServiceGrpc.LevelUpServiceImplBase {
        @Override
        public void levelUp(ru.denisaql.grpc.LevelUpRequest request,
                            io.grpc.stub.StreamObserver<ru.denisaql.grpc.LevelUpResponse> responseObserver) {
            System.out.println(request);

            int newLevel = request.getCurrentLevel() + 1;

            LevelUpResponse response = LevelUpResponse.newBuilder()
                    .setName(request.getName())
                    .setNewLevel(newLevel)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
