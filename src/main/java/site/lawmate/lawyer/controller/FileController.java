package site.lawmate.lawyer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.File;
import site.lawmate.lawyer.service.impl.FileServiceImpl;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping("/files")
public class FileController {

    private final FileServiceImpl fileService;

    @PostMapping("/upload/{lawyerId}")
    public ResponseEntity<Flux<File>> uploadFile(@PathVariable("lawyerId")String lawyerId,
                                                 @RequestPart("file") Flux<FilePart> fileParts) {
        return ResponseEntity.ok(fileService.saveFiles(lawyerId, fileParts));
    }

    @GetMapping("/download/{id}")
    public Mono<ResponseEntity<ByteArrayResource>> downloadFile(@PathVariable("id") String id) {
        return fileService.getFileById(id)
                .map(file -> {
                    ByteArrayResource resource = new ByteArrayResource(file.getData());
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                            .contentType(MediaType.parseMediaType(file.getContentType()))
                            .body(resource);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteFile(@PathVariable("id") String id) {
        return fileService.deleteFileById(id);
    }

    @DeleteMapping("/delete")
    public Mono<Void> deleteAllFiles() {
        return fileService.deleteAllFiles();
    }

}
