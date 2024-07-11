package site.lawmate.lawyer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.Reservation;
import site.lawmate.lawyer.service.impl.ReservationServiceImpl;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/reservation")
public class ResController {
    private final ReservationServiceImpl service;


//    @GetMapping("/{date}")
//    public Flux<ResModel> getReservationsForDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        return service.getReservationsByDate(date);
//    }

    @PostMapping("/save")
    public ResponseEntity<Mono<Reservation>> createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(service.createReservation(reservation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mono<Reservation>> updateReservation(@PathVariable("id") String id, @RequestBody Reservation res) {
        return ResponseEntity.ok(service.updateReservation(id, res));
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<Reservation>> getAllReservations() {
        return ResponseEntity.ok(service.getAllReservations());
    }

//    @GetMapping("/findByLawyerId/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<ResModel> getReservationByLawyerId(@PathVariable("id") String id) {
//        return service.getReservationByLawyerId(id);
//    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Mono<Void>> deleteReservation(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.deleteReservation(id));
    }
}
