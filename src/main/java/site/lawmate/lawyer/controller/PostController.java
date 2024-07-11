package site.lawmate.lawyer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.Lawyer;
import site.lawmate.lawyer.domain.model.Post;
import site.lawmate.lawyer.service.impl.PostServiceImpl;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/posts")
public class PostController {
    private final PostServiceImpl service;
    

    @PostMapping("/save/{id}")
    public ResponseEntity<Mono<Lawyer>> createPost(@PathVariable("id") String lawyerId, @RequestBody Post post) {
        return ResponseEntity.ok(service.postToLawyer(lawyerId, post));

    }
    @GetMapping("/{id}")
    public ResponseEntity<Flux<Post>> getPostsByLawyerId(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getPostsByLawyerId(id));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Mono<Post>> updatePost(
            @PathVariable("postId") String postId,
            @RequestBody Post updatedPost) {
        return ResponseEntity.ok(service.updatePost(postId, updatedPost));
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<Post>> getAllPosts() {
        return ResponseEntity.ok(service.getAllPosts());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deletePost(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.deletePost(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Mono<Void>> deleteAllPosts() {
        return ResponseEntity.ok(service.deleteAllPosts());
    }

}
