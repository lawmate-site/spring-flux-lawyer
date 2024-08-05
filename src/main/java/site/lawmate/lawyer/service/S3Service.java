package site.lawmate.lawyer.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayInputStream;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;
    private final String bucketName;

    public Mono<String> uploadFile(FilePart filePart) {
        return filePart.content().reduce(DataBuffer::write)
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    String key = UUID.randomUUID().toString() + "_" + filePart.filename();
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(bytes.length);
                    metadata.setContentType(filePart.headers().getContentType().toString());

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
                    s3Client.putObject(putObjectRequest);

                    return Mono.just(s3Client.getUrl(bucketName, key).toString());
                }).subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<String> uploadFiles(Flux<FilePart> fileParts) {
        return fileParts.flatMap(this::uploadFile);
    }
}
