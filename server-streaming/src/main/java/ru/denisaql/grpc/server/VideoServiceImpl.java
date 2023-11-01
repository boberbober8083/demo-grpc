package ru.denisaql.grpc.server;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import ru.denisaql.grpc.GetVideoRequest;
import ru.denisaql.grpc.VideoFrame;
import ru.denisaql.grpc.VideoServiceGrpc;

public class VideoServiceImpl extends VideoServiceGrpc.VideoServiceImplBase {
    @Override
    public void subscribeToVideo(GetVideoRequest request, StreamObserver<VideoFrame> responseObserver) {

        for (int i = 1; i <= 5; ++i) {
            byte[] videoFrameBytes = {1, (byte) i, 3, (byte) i};
            VideoFrame frame = VideoFrame.newBuilder()
                    .setContent(ByteString.copyFrom(videoFrameBytes))
                    .build();
            responseObserver.onNext(frame);
        }
        responseObserver.onCompleted();
    }
}
