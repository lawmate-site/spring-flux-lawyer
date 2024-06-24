package site.lawmate.lawyer.component;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log
@Component
@RequiredArgsConstructor
public class AuthManager implements ReactiveAuthenticationManager {



    private final JwtProvider tokenProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // return Mono.just(authentication)
        //         .cast(JwtToken.class)
        //         .filter(jwtToken -> tokenProvider.isTokenValid(jwtToken.getToken()))
        //         .map(jwtToken -> jwtToken.withAuthenticated(true))
        //         .switchIfEmpty(Mono.error(new JwtAuthenticationException("Invalid token.")));
        return Mono.empty();
    }

}
