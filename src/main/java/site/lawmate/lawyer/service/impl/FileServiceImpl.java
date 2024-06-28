package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.FileModel;
import site.lawmate.lawyer.repository.FileRepository;
import site.lawmate.lawyer.repository.LawyerRepository;

@Service
@RequiredArgsConstructor
public class FileServiceImpl {

    private final FileRepository fileRepository;
    private final LawyerRepository lawyerRepository;

    public Mono<FileModel> saveFile(String lawyerId, FilePart filePart) {
        return lawyerRepository.findById(lawyerId)
                .flatMap(lawyer -> filePart.content()
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
                        FileModel fileModel = new FileModel();
                        fileModel.setFilename(filePart.filename());
                        MediaType mediaType = filePart.headers().getContentType();
                        fileModel.setContentType(mediaType != null ? mediaType.toString() : "application/octet-stream");
                        fileModel.setData(bytes);
                        fileModel.setLawyerId(lawyerId);
                        return fileRepository.save(fileModel);
                    })
                );
    }

    public Mono<FileModel> getFileById(String id) {
        return fileRepository.findById(id);
    }

}
