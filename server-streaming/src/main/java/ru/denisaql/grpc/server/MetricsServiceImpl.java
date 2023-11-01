package ru.denisaql.grpc.server;

import io.grpc.stub.StreamObserver;
import ru.denisaql.grpc.Average;
import ru.denisaql.grpc.MetricBundle;
import ru.denisaql.grpc.MetricsServiceGrpc;

public class MetricsServiceImpl extends MetricsServiceGrpc.MetricsServiceImplBase {


    @Override
    public StreamObserver<MetricBundle> collect(StreamObserver<Average> responseObserver) {
        return new StreamObserver<MetricBundle>() {
            private long sum = 0;
            private long count = 0;

            @Override
            public void onNext(MetricBundle value) {
                System.out.println("value: " + value);
                sum += value.getMetric();
                ++count;
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                if (count == 0) {
                    responseObserver.onNext(Average.newBuilder().build());
                } else {
                    responseObserver.onNext(Average.newBuilder()
                            .setVal(sum / count)
                            .build());
                }
            }
        };
    }
}