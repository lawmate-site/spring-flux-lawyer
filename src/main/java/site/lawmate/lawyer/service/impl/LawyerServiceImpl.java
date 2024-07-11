package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
//    private final JwtProvider jwtProvider;
//    private final TokenServiceImpl tokenService;

//    @Value("${jwt.expiration.refresh}")
//    private long refreshTokenExpiration;
//
//    @Value("${jwt.expiration.access}")
//    private long accessTokenExpiration;

    public Mono<Messenger> login(LawyerDto lawyerDto) {
        return lawyerRepository.findByUsername(lawyerDto.getUsername())
                .flatMap(lawyerModel -> {
                    boolean isPasswordCorrect = lawyerModel.getPassword().equals(lawyerDto.getPassword());
                    if (isPasswordCorrect) {
                        return Mono.just(Messenger.builder().message("SUCCESS").data(lawyerDto).build());
                    } else {
                        log.info("비밀번호가 틀렸습니다.");
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("존재하지 않는 아이디입니다."))
                        .then(Mono.just(Messenger.builder().message("FAILURE").build())));
    }

    public Flux<Lawyer> getAllLawyers() {
        return lawyerRepository.findAll();
    }

    public Mono<Long> getLawyersCount() {
        return lawyerRepository.count();
    }

    public Mono<Lawyer> getLawyerUsernameByEmail(String email) {
        return lawyerRepository.findByUsername(email);
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
                });}

    public Mono<LawyerDetail> getLawyerDetailById(String id) {
        return lawyerRepository.findById(id)
                .map(Lawyer::getDetail)
                ;}

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

    // 시큐리티 로그인

//    public Mono<Messenger> login(Lawyer lawyer) {
//        log.info("로그인에 사용되는 이메일 : {}",lawyer.getEmail());
//
//        var accessToken = jwtProvider.generateToken(null, lawyer, "accessToken");
//        if(accessToken.equals("")){
//            log.info("접속토큰 발급 실패");
//        }
//
//        var refreshToken = jwtProvider.generateToken(null, lawyer, "refreshToken");
//        if(refreshToken.equals("")){
//            log.info("리프레시 토큰 발급 실패");
//        }
//
//        log.info("로그인 성공시 접속토큰  : {}", accessToken);
//        log.info("로그인 성공시 재생토큰  : {}", refreshToken);
//
//        tokenService.saveRefreshToken(lawyer.getEmail(), refreshToken, refreshTokenExpiration);
//
//
//        // Sync
//        return lawyerRepository.findByEmail(lawyer.getEmail())
//                .filter(i -> i.getPassword().equals(lawyer.getPassword()))
//                .map(i -> LawyerDTO.builder().email(i.getEmail()).firstName(i.getFirstName()).lastName(i.getLastName()).build())
//                .log()
//                .map(i -> Messenger.builder().message("SUCCESS").data(i)
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .accessTokenExpiration(accessTokenExpiration)
//                        .refreshTokenExpiration(refreshTokenExpiration)
//                        .build());
//    }
//    public Mono<Messenger> login2(Lawyer lawyer) {
//        log.info("로그인 2 에 사용되는 이메일 : {}",lawyer.getEmail());
//        // Async
//        // attach 방식으로 사용
//        return lawyerRepository.findByEmail(lawyer.getEmail())
//                .filter(i -> i.getPassword().equals(lawyer.getEmail()))
//                .flatMap(i -> Mono.just(LawyerDTO.builder().email(i.getEmail()).firstName(i.getFirstName()).lastName(i.getLastName()).build()))
//                .log()
//                .flatMap(i -> Mono.just(Messenger.builder().data(i).build()))
//
//                ;
//    }
}