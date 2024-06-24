package site.lawmate.lawyer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.ReplyModel;
import site.lawmate.lawyer.service.impl.ReplyServiceImpl;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/api/replies")
public class ReplyController {
    private final ReplyServiceImpl service;

    @PostMapping("/add/{lawyerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ReplyModel> createReply(@PathVariable("lawyerId") String lawyerId, @RequestBody ReplyModel reply) {
        return service.createReply(lawyerId, reply);
    }

    @GetMapping("/myReply/{lawyerId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ReplyModel> getRepliesByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        return service.getRepliesByLawyerId(lawyerId);
    }
    @PatchMapping("/update/{id}")
    public Mono<ReplyModel> updateReply(@PathVariable("id") String id, @RequestBody ReplyModel reply) {
        return service.updateReply(id, reply);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ReplyModel> getAllReplies() {
        return service.getAllReplies();
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteReply(@PathVariable("id") String id) {
        return service.deleteReply(id);
    }
}
