package edu.eci.cvds.reservas.controller;

import edu.eci.cvds.reservas.model.Reservation;
import edu.eci.cvds.reservas.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }


    @GetMapping("/get/count")
    public ResponseEntity<Long> getReservationCount() {
        return ResponseEntity.ok(reservationService.getReservationCount());
    }

    @PostMapping("/post")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

    @PostMapping("/post/multiple")
    public ResponseEntity<List<Reservation>> createMultipleReservations(@RequestBody List<Reservation> reservations) {
        return ResponseEntity.ok(reservationService.saveMultipleReservations(reservations));
    }

    @PostMapping("/post/generate-random")
    public ResponseEntity<String> generateRandomReservations() {
        reservationService.generateRandomReservations(0);
        return ResponseEntity.ok("Reservas aleatorias generadas exitosamente.");
    }

    @PostMapping("/post/generate-random/{quantity}")
    public ResponseEntity<String> generateRandomReservationsQuantity(@PathVariable int quantity) {
        reservationService.generateRandomReservations(quantity);
        return ResponseEntity.ok("Reservas aleatorias generadas exitosamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllReservations() {
        reservationService.deleteAllReservations();
        return ResponseEntity.ok("Todas las reservas han sido eliminadas.");
    }

}
