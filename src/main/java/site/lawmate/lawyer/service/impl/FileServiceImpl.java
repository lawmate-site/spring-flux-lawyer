package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.File;
import site.lawmate.lawyer.repository.FileRepository;
import site.lawmate.lawyer.repository.LawyerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl {

    private final FileRepository fileRepository;
    private final LawyerRepository lawyerRepository;

    public Flux<File> saveFiles(String lawyerId, Flux<FilePart> fileParts) {
        return lawyerRepository.findById(lawyerId)
                .flatMapMany(lawyer -> fileParts
                        .flatMap(filePart -> filePart.content()
                                .map(dataBuffer -> {
                                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(bytes);
                                    DataBufferUtils.release(dataBuffer);
                                    return bytes;
                                })
                                .reduce((byte1, byte2) -> {
                                    byte[] combined = new byte[byte1.length + byte2.length];
                                    System.arraycopy(byte1, 0, combined, 0, byte1.length);
                                    System.arraycopy(byte2, 0, combined, byte1.length, byte2.length);
                                    return combined;
                                })
                                .flatMap(bytes -> {
                                    File file = new File();
                                    file.setFilename(filePart.filename());
                                    MediaType mediaType = filePart.headers().getContentType();
                                    file.setContentType(mediaType != null ? mediaType.toString() : "application/octet-stream");
                                    file.setData(bytes);
                                    file.setLawyerId(lawyerId);
                                    return fileRepository.save(file);
                                })
                        )
                );
    }

    public Mono<File> getFileById(String id) {
        return fileRepository.findById(id);
    }

    public Mono<Void> deleteFileById(String id){
        return fileRepository.deleteById(id);
    }

    public Mono<Void> deleteAllFiles() {
        return fileRepository.deleteAll();
    }
}
