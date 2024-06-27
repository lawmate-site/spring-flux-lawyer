package site.lawmate.lawyer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.NoticeModel;
import site.lawmate.lawyer.service.impl.NoticeServiceImpl;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/notifications")
public class NoticeController {
    private final NoticeServiceImpl service;

    @PostMapping("/save")
    public ResponseEntity<Mono<NoticeModel>> createNoticeModel(@RequestBody NoticeModel notification) {
        return ResponseEntity.ok(service.createNoticeModel(notification));
    }

    @PostMapping("/respond/{id}")
    public ResponseEntity<Mono<NoticeModel>> respondToNoticeModel(@PathVariable("id") String id, @RequestParam("status") String status) {
        return ResponseEntity.ok(service.updateNoticeModelStatus(id, status));
    }

    @GetMapping(value = "/lawyer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<NoticeModel>> subscribeToLawyerNoticeModels() {
        return ResponseEntity.ok(service.getLawyerNoticeModels());
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<NoticeModel>> subscribeToUserNoticeModels(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(service.getUserNoticeModels(userId));
    }

    @GetMapping("/lawyer/{lawyerId}")
    public ResponseEntity<Flux<NoticeModel>> getLawyerNotificationsByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        return ResponseEntity.ok(service.getNotificationsByLawyerId(lawyerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteNotification(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.deleteNotification(id));
    }
}
