package site.lawmate.lawyer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.ResModel;
import site.lawmate.lawyer.service.impl.ResServiceImpl;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/api/reservation")
public class ResController {
    private final ResServiceImpl service;


//    @GetMapping("/{date}")
//    public Flux<ResModel> getReservationsForDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        return service.getReservationsByDate(date);
//    }

    @PostMapping("/add")
    public Mono<ResModel> createReservation(@RequestBody ResModel reservation) {
        return service.createReservation(reservation);
    }

    @PatchMapping("/update/{id}")
    public Mono<ResModel> updateReservation(@PathVariable("id") String id, @RequestBody ResModel res) {
        return service.updateReservation(id, res);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ResModel> getAllReservations() {
        return service.getAllReservations();
    }

//    @GetMapping("/findByLawyerId/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<ResModel> getReservationByLawyerId(@PathVariable("id") String id) {
//        return service.getReservationByLawyerId(id);
//    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteReservation(@PathVariable("id") String id) {
        return service.deleteReservation(id);
    }
}
