package site.lawmate.lawyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.FileModel;
import site.lawmate.lawyer.repository.FileRepository;

import java.io.File;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public Mono<FileModel> saveFile(FilePart filePart) {
        return filePart.content()
                .map(this::toByteArray)
                .reduce(this::concatArrays)
                .flatMap(bytes -> {
                    FileModel fileModel = new FileModel();
                    fileModel.setFilename(filePart.filename());
                    MediaType mediaType = filePart.headers().getContentType();
                    fileModel.setContentType(mediaType != null ? mediaType.toString() : "application/octet-stream");
                    fileModel.setData(bytes);
                    return fileRepository.save(fileModel);
                });
    }

    public Mono<FileModel> getFileById(String id) {
        return fileRepository.findById(id);
    }

    private byte[] toByteArray(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);
        return bytes;
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
