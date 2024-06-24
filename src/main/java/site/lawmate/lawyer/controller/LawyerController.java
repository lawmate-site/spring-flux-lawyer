package site.lawmate.lawyer.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.component.Messenger;
import site.lawmate.lawyer.domain.dto.LawyerDto;
import site.lawmate.lawyer.domain.model.LawyerDetailModel;
import site.lawmate.lawyer.domain.model.LawyerModel;
import site.lawmate.lawyer.service.impl.LawyerServiceImpl;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/api/lawyers")
public class LawyerController {


    private final LawyerServiceImpl lawyerService;

    @PostMapping("/login")
    public Mono<Messenger> login(@RequestBody LawyerDto lawyerDto) {
        log.info("::: 로그인 컨트롤러 파라미터 : {}",lawyerDto.toString());
        return lawyerService.login(lawyerDto);
    }

    @GetMapping("/logout")
    public Mono<Messenger> logout(@RequestHeader("Authorization") String accessToken) {
        log.info("1- 로그아웃 접속토큰 : {}", accessToken);
        Messenger m = Messenger.builder().message("SUCCESS").build();
        Mono<Messenger> logout = Mono.just(m);
        return logout;
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Long> getLawyersCount() {
        return lawyerService.getLawyersCount();
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Flux<LawyerModel> getAllLawyers() {
        return lawyerService.getAllLawyers();
    }

    @GetMapping("/findId")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LawyerModel> getLawyerUsernameByEmail(@RequestParam String email) {
        return lawyerService.getLawyerUsernameByEmail(email);
    }

    @GetMapping("/myPage/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LawyerModel> getLawyerById(@PathVariable("id") String id) {
        return lawyerService.getLawyerById(id);
    }

    @GetMapping("/myDetailPage/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LawyerDetailModel> getLawyerDetailById(@PathVariable("id") String id) {
        return lawyerService.getLawyerDetailById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Messenger> saveLawyer(@RequestBody LawyerModel lawyer) {
        return lawyerService.addLawyer(lawyer);
    }

    @PostMapping("/addDetail/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LawyerModel> saveLawyerDetail(@PathVariable("id") String lawyerId, @RequestBody LawyerDetailModel lawyer) {
        return lawyerService.addLawyerDetailToLawyer(lawyerId, lawyer);
    }
    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LawyerModel> updateLawyer(@PathVariable("id") String id, @RequestBody LawyerModel lawyer) {
        return lawyerService.updateLawyer(id, lawyer);
    }

    @PatchMapping("/updateDetail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LawyerModel> updateLawyerDetail(@PathVariable("id") String id, @RequestBody LawyerDetailModel lawyer) {
        return lawyerService.updateLawyerDetail(id, lawyer);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteLawyer(@PathVariable("id") String id) {
        return lawyerService.deleteLawyer(id);
    }

    @DeleteMapping("/allDelete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAllLawyers() {
        return lawyerService.deleteAllLawyers();
    }


}