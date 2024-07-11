package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.Reservation;
import site.lawmate.lawyer.repository.ReservationRepository;
import site.lawmate.lawyer.service.ReservationService;


@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    public Mono<Reservation> createReservation(Reservation res) {
        return reservationRepository.save(res);
    }

    public Mono<Reservation> updateReservationStatus(String id, Reservation res) {
        return reservationRepository.findById(id)
                .flatMap(reservation -> {
                    reservation.setStatus(res.getStatus());
                    return reservationRepository.save(reservation);
                });
    }

    public Flux<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Flux<Reservation> getReservationByLawyerId(String lawyerId) {
        return reservationRepository.findByLawyerId(lawyerId);
    }

    public Mono<Void> deleteReservation(String id) {
        return reservationRepository.deleteById(id);
    }

}
