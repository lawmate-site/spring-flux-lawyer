package site.lawmate.lawyer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import site.lawmate.lawyer.domain.model.NoticeModel;

@Repository
public interface NoticeRepository extends ReactiveMongoRepository<NoticeModel, String> {
    Flux<NoticeModel> findByUserId(String userId);
}
