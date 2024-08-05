package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.component.Messenger;
import site.lawmate.lawyer.domain.dto.LawyerDto;
import site.lawmate.lawyer.domain.model.LawyerDetail;
import site.lawmate.lawyer.domain.model.Lawyer;
import site.lawmate.lawyer.repository.LawyerDetailRepository;
import site.lawmate.lawyer.repository.LawyerRepository;
import site.lawmate.lawyer.service.LawyerService;

@Service
@Slf4j
@RequiredArgsConstructor
public class LawyerServiceImpl implements LawyerService {

    private final LawyerRepository lawyerRepository;
    private final LawyerDetailRepository lawyerDetailRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
//    private final JwtProvider jwtProvider;
//    private final TokenServiceImpl tokenService;

//    @Value("${jwt.expiration.refresh}")
//    private long refreshTokenExpiration;
//
//    @Value("${jwt.expiration.access}")
//    private long accessTokenExpiration;


    public Flux<Lawyer> getAllLawyers() {
        return lawyerRepository.findAll();
    }

    public Mono<Long> getLawyersCount() {
        return lawyerRepository.count();
    }


    public Mono<Lawyer> getLawyerById(String id) {
        return lawyerRepository.findById(id);
    }


    // 변호사 추가 정보
    public Mono<Lawyer> addLawyerDetailToLawyer(String id, LawyerDetail detail) {
        return lawyerRepository.findById(id)
                .flatMap(lawyer -> {
                    return lawyerDetailRepository.save(detail)
                            .flatMap(savedDetail -> {
                                lawyer.setDetail(savedDetail);
                                return lawyerRepository.save(lawyer);
                            });
                });
    }

    public Mono<LawyerDetail> getLawyerDetailById(String id) {
        return lawyerRepository.findById(id)
                .map(Lawyer::getDetail)
                ;
    }

    public Mono<Lawyer> addLawyer(Lawyer lawyer) {
        return lawyerRepository.save(lawyer);
    }

    public Mono<Lawyer> updateLawyer(String id, Lawyer lawyer) {
        return lawyerRepository.findById(id)
                .flatMap(optionalLawyer -> {
                    if (lawyer.getPassword() != null) {
                        optionalLawyer.setPassword(lawyer.getPassword());
                    }
                    if (lawyer.getMid() != null) {
                        optionalLawyer.setMid(lawyer.getMid());
                    }
                    if (lawyer.getPhone() != null) {
                        optionalLawyer.setPhone(lawyer.getPhone());
                    }
                    return lawyerRepository.save(optionalLawyer);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Void> deleteLawyer(String id) {
        return lawyerRepository.deleteById(id);
    }

    public Flux<Lawyer> findByName(String lastName) {
        return lawyerRepository.findByName(lastName);
    }

    public Mono<Lawyer> updateLawyerDetail(String id, LawyerDetail detail) {
        return lawyerRepository.findById(id)
                .flatMap(lawyer -> {
                    return lawyerDetailRepository.save(detail)
                            .flatMap(savedDetail -> {
                                lawyer.setDetail(savedDetail);
                                return lawyerRepository.save(lawyer);
                            });
                });
    }

    public Mono<Lawyer> getLawyerByEmail(String username) {
        return lawyerRepository.findByEmail(username);
    }

    public Mono<LawyerDetail> getLawyerDetailByEmail(String email) {
        return lawyerRepository.findByEmail(email)
                .map(Lawyer::getDetail);
    }

    public Flux<Lawyer> getLawyersByLaw(String law) {
        Query query = new Query();
        query.addCriteria(Criteria.where("detail.law").is(law));
//        query.addCriteria(Criteria.where("detail.law").is(law).and("auth").is(true));
        query.with(Sort.by(Sort.Order.desc("detail.premium")));

        return reactiveMongoTemplate.find(query, Lawyer.class);
    }

    public Flux<Lawyer> getLawyersBySearch(String search) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        String regex = ".*" + search + ".*";
        criteria.orOperator(
                Criteria.where("detail.law").regex(regex),
                Criteria.where("name").regex(regex),
                Criteria.where("detail.belong").regex(regex),
                Criteria.where("detail.address").regex(regex)
        );
//        criteria.andOperator(Criteria.where("auth").is(true));
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Order.desc("detail.premium")));

        return reactiveMongoTemplate.find(query, Lawyer.class);
    }
}