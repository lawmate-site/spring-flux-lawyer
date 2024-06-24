package site.lawmate.lawyer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import site.lawmate.lawyer.domain.model.ResModel;

@Repository
public interface ResRepository extends ReactiveMongoRepository<ResModel, String> {
//    Flux<ResModel> findByDate(LocalDate date);

//    Mono<ResModel> findByLawyerId(String id);
}
