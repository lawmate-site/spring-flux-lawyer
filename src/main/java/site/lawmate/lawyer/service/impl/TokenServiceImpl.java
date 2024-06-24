package site.lawmate.lawyer.service.impl;
//
//import com.example.webflux.security.domain.TokenModel;
//import com.example.webflux.security.repository.TokenRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.time.Instant;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class TokenServiceImpl implements TokenService{
//    private final TokenRepository tokenRepository;
//
//    public void saveRefreshToken(String email, String refreshToken, long refreshTokenExpiration){
//
//        TokenModel token = TokenModel.builder()
//                .email(email)
//                .refreshToken(refreshToken)
//                .expiration(Date.from(Instant.now().plusSeconds(refreshTokenExpiration)))
//                .build();
//
//        log.info("Service - TokenModel token : {}",token);
//
//        tokenRepository.save(token)
//                .flatMap(i -> Mono.just(i.getRefreshToken())).subscribe();
//
//    }
//
//}
