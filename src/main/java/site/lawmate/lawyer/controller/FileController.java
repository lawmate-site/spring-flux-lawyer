package site.lawmate.lawyer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.FileModel;
import site.lawmate.lawyer.service.FileService;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Mono<FileModel>> uploadFile(@RequestPart("file") FilePart filePart) {
        if (filePart != null) {
            log.info("파일 이름 : " + filePart.filename());
        } else {
            log.info("파일이 존재하지 않습니다.");
        }

        return ResponseEntity.ok(fileService.saveFile(filePart));
    }

    @GetMapping("/download/{id}")
    public Mono<ResponseEntity<ByteArrayResource>> downloadFile(@PathVariable("id") String id) {
        return fileService.getFileById(id)
                .map(fileModel -> {
                    ByteArrayResource resource = new ByteArrayResource(fileModel.getData());
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getFilename() + "\"")
                            .contentType(MediaType.parseMediaType(fileModel.getContentType()))
                            .body(resource);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
