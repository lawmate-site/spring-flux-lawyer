package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.LawyerModel;
import site.lawmate.lawyer.domain.model.PostModel;
import site.lawmate.lawyer.repository.LawyerRepository;
import site.lawmate.lawyer.repository.PostRepository;
import site.lawmate.lawyer.service.PostService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final LawyerRepository lawyerRepository;

    public Mono<LawyerModel> postToLawyer(String id, PostModel post) {
        return lawyerRepository.findById(id)
                .flatMap(lawyer -> {
                    post.setLawyerId(lawyer.getId());
                    return postRepository.save(post)
                            .then(Mono.just(lawyer));
                })
                .flatMap(lawyer -> postRepository.findByLawyerId(lawyer.getId()).collectList()
                        .flatMap(posts -> {
                            lawyer.setPosts(posts);
                            return Mono.just(lawyer);
                        })
                );
    }

    public Flux<PostModel> getPostsByLawyerId(String lawyerId) {
        return postRepository.findAllByLawyerId(lawyerId);
    }

    public Mono<PostModel> updatePost(String postId, PostModel postModel) {
        return postRepository.findById(postId)
                .flatMap(post -> {
                    post.setTitle(postModel.getTitle());
                    post.setContent(postModel.getContent());
                    post.setCategory(postModel.getCategory());
                    return postRepository.save(post);
                });
    }

    public Flux<PostModel> getAllPosts() {
        return postRepository.findAll();
    }

    public Mono<Void> deletePost(String id) {
        return postRepository.deleteById(id);
    }
}
