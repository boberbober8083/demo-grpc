package ru.denisaql.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.denisaql.grpc.Average;
import ru.denisaql.grpc.GetVideoRequest;
import ru.denisaql.grpc.MetricBundle;
import ru.denisaql.grpc.MetricsServiceGrpc;
import ru.denisaql.grpc.VideoFrame;
import ru.denisaql.grpc.VideoServiceGrpc;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class StreamingClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 10081)
                .usePlaintext().build();

        //proceedClientStreaming(channel);
        proceedServerStreaming(channel);

        channel.shutdown().awaitTermination(60, TimeUnit.SECONDS);
    }

    private static void proceedServerStreaming(ManagedChannel channel) {
        VideoServiceGrpc.VideoServiceBlockingStub stub = VideoServiceGrpc.newBlockingStub(channel);
        GetVideoRequest request = GetVideoRequest.newBuilder()
                .setTitle("test")
                .build();

        Iterator<VideoFrame> iterator = stub.subscribeToVideo(request);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void proceedClientStreaming(ManagedChannel channel) throws InterruptedException {
        MetricsServiceGrpc.MetricsServiceStub stub = MetricsServiceGrpc.newStub(channel);

        StreamObserver<MetricBundle> collect = stub.collect(new StreamObserver<Average>() {
            @Override
            public void onNext(Average value) {
                System.out.println("Average: " + value.getVal());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });

        Stream.of(1L, 2L, 3L, 4L)
                .map(l -> MetricBundle.newBuilder().setMetric(l).build())
                .forEach(item -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    collect.onNext(item);
                });

        collect.onCompleted();
    }
}
