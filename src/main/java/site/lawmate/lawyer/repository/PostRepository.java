package site.lawmate.lawyer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import site.lawmate.lawyer.domain.model.PostModel;

@Repository
public interface PostRepository extends ReactiveMongoRepository<PostModel, String> {
    Flux<PostModel> findByLawyerId(String lawyerId);
}
