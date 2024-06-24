package site.lawmate.lawyer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import site.lawmate.lawyer.domain.model.ReplyModel;

@Repository
public interface ReplyRepository extends ReactiveMongoRepository<ReplyModel, String>{
    Flux<ReplyModel> findByLawyerId(String lawyerId);
//    Mono<ReplyModel> findByLawyerId(String id);
}
