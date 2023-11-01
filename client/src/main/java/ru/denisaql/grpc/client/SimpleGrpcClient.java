package ru.denisaql.grpc.client;

import com.google.protobuf.Descriptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.denisaql.grpc.GreetingServiceGrpc;
import ru.denisaql.grpc.HelloRequest;
import ru.denisaql.grpc.HelloResponse;
import ru.denisaql.grpc.LevelUpRequest;
import ru.denisaql.grpc.LevelUpResponse;
import ru.denisaql.grpc.LevelUpServiceGrpc;

import java.util.Map;

public class SimpleGrpcClient {
  public static void main(String[] args) {
    SimpleGrpcClient app = new SimpleGrpcClient();
    app.run();

  }

  public void run() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 10080)
            .usePlaintext()
            .build();

    hello(channel);
    levelUp(channel);

    channel.shutdown();
  }


  public void hello(ManagedChannel channel) {
    GreetingServiceGrpc.GreetingServiceBlockingStub stub =
            GreetingServiceGrpc.newBlockingStub(channel);

    HelloResponse helloResponse = stub.greeting(
            HelloRequest.newBuilder()
                    .setName("Slava")
                    .setAge(23)
                    .build());

    System.out.println(helloResponse);
  }

  public void levelUp(ManagedChannel channel) {
    LevelUpServiceGrpc.LevelUpServiceBlockingStub stub =
            LevelUpServiceGrpc.newBlockingStub(channel);

    LevelUpRequest request = LevelUpRequest.newBuilder()
            .setName("Super Mario")
            .setCurrentLevel(80)
            .build();

    LevelUpResponse resp = stub.levelUp(request);
    System.out.println(resp.getName());
  }
}
