package edu.eci.cvds.reservas.controller;

import edu.eci.cvds.reservas.model.Reservation;
import edu.eci.cvds.reservas.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private ReservationService reservationService;

    @DeleteMapping("/delete/all")
    public String deleteAllReservations() {
        reservationService.deleteAllReservations();
        return "Todas las reservas han sido eliminadas.";
    }

    @PostMapping("/post/generate-random")
    public String generateRandomReservations() {
        reservationService.generateRandomReservations();
        return "Reservas aleatorias generadas exitosamente.";
    }

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/get/all")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping("/post/")
    public Reservation saveReservation(@RequestBody Reservation reserva) {
        return reservationService.saveReservation(reserva);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
    }


}
