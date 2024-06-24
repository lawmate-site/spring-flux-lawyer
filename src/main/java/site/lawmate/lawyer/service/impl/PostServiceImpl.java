package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.PostModel;
import site.lawmate.lawyer.repository.LawyerRepository;
import site.lawmate.lawyer.repository.PostRepository;
import site.lawmate.lawyer.service.PostService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final LawyerRepository lawyerRepository;


    public Mono<PostModel> createPost(String lawyerId, PostModel post) {
        post.setLawyerId(lawyerId);
        return postRepository.save(post);
    }
    public Flux<PostModel> getPostsByLawyerId(String lawyerId) {
        return postRepository.findByLawyerId(lawyerId);
    }

    public Mono<PostModel> updatePost(String id, PostModel post) {
        return postRepository.findById(id)
                .flatMap(i->{
                    i.setTitle(post.getTitle());
                    i.setContent(post.getContent());
                    return postRepository.save(i);
                });
    }

    public Flux<PostModel> getAllPosts() {
        return postRepository.findAll();
    }

    public Mono<Void> deletePost(String id) {
        return postRepository.deleteById(id);
    }
}
