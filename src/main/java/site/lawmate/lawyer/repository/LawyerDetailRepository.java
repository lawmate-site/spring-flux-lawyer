package site.lawmate.lawyer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import site.lawmate.lawyer.domain.model.LawyerDetailModel;

@Repository
public interface LawyerDetailRepository extends ReactiveMongoRepository<LawyerDetailModel, String>{
}
