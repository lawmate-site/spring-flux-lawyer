package site.lawmate.lawyer.service;

import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.dto.LoginDTO;
import site.lawmate.lawyer.domain.dto.PrincipalUserDetails;


public interface LoginService {

    Mono<PrincipalUserDetails> login(LoginDTO admin);

}
