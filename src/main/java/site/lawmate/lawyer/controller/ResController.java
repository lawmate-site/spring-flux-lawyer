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
import site.lawmate.lawyer.domain.model.ResModel;
import site.lawmate.lawyer.service.impl.ResServiceImpl;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/reservation")
public class ResController {
    private final ResServiceImpl service;


//    @GetMapping("/{date}")
//    public Flux<ResModel> getReservationsForDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        return service.getReservationsByDate(date);
//    }

    @PostMapping("/save")
    public ResponseEntity<Mono<ResModel>> createReservation(@RequestBody ResModel reservation) {
        return ResponseEntity.ok(service.createReservation(reservation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mono<ResModel>> updateReservation(@PathVariable("id") String id, @RequestBody ResModel res) {
        return ResponseEntity.ok(service.updateReservation(id, res));
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<ResModel>> getAllReservations() {
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
