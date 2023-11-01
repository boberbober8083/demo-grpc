package ru.denisaql.grpc.server;

import ru.denisaql.grpc.LevelUpResponse;
import ru.denisaql.grpc.LevelUpServiceGrpc;

public class LevelUpServiceImpl extends LevelUpServiceGrpc.LevelUpServiceImplBase {
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
