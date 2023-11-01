package ru.denisaql.grpc.server;

import io.grpc.CompressorRegistry;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SimpleGrpcServer {
    static public void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(10081)
                .compressorRegistry(CompressorRegistry.getDefaultInstance())
                .addService(new MetricsServiceImpl())
                .addService(new VideoServiceImpl())
                .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
