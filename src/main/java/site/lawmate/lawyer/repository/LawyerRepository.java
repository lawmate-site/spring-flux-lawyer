package site.lawmate.lawyer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.LawyerDetailModel;
import site.lawmate.lawyer.domain.model.LawyerModel;


@Repository
public interface LawyerRepository extends ReactiveMongoRepository<LawyerModel, String> {

    Flux<LawyerModel> findByName(String name);
    Mono<LawyerModel> findByUsername(String username);
    Mono<LawyerModel> findByDetail(LawyerDetailModel detail);


}
