package edu.eci.cvds.reservas.service;

import edu.eci.cvds.reservas.model.Reservation;
import edu.eci.cvds.reservas.repository.reservation.ReservationRepository;
import edu.eci.cvds.reservas.repository.reservation.ReservationRepositoryMongo;
import edu.eci.cvds.reservas.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class ReservationService {

    @Autowired
    private ReservationRepositoryMongo reservationRepository;

    private final Random random = new Random();

    private static final String[] LABORATORIES = {"Lab A", "Lab B", "Lab C", "Lab D"};
    private static final String[] USERS = {"user1", "user2", "user3", "user4"};
    private static final String[] CLASSES = {"Math", "Physics", "Computer Science", "Biology"};

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 6;
    private final SecureRandom secureRandom = new SecureRandom();

    private String generateUniqueId() {
        String id;
        do {
            id = generateRandomId();
        } while (reservationRepository.existsById(id)); // Verificar si ya existe en la DB
        return id;
    }

    private String generateRandomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(secureRandom.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    // DELETE: All Reservations
    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }


    // POST: Lab 5 New Feature
    public void generateRandomReservations(int quantity) {
        int numReservations;
        if (quantity > 0) {
            numReservations = quantity;
        } else {
            numReservations = random.nextInt(901) + 100; // Genera entre 100 y 1000 reservas
        }

        List<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < numReservations; i++) {
            Reservation reservation = new Reservation();

            // Random Data
            reservation.setId(generateUniqueId());
            reservation.setLaboratory(LABORATORIES[random.nextInt(LABORATORIES.length)]);
            reservation.setDateHour(LocalDateTime.now().plusDays(random.nextInt(30)));
            reservation.setUser(USERS[random.nextInt(USERS.length)]);
            reservation.setClassName(CLASSES[random.nextInt(CLASSES.length)]);
            reservation.setCreationDate(LocalDateTime.now());
            reservation.setPriority(random.nextInt(5) + 1);
            reservations.add(reservation);
        }

        reservationRepository.saveAll(reservations);
    }

    public Optional<Reservation> getReservationById(String id) {
        return reservationRepository.findById(id);
    }

    public Long getReservationCount() {
        return reservationRepository.count();
    }

    public List<Reservation> saveMultipleReservations(List<Reservation> reservations) {
        return reservationRepository.saveAll(reservations);
    }


    // GET: Obtain all Reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAllReservations();
    }

    // ADD: Save or Update a Reservation
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.saveReservation(reservation);
    }

    // DELETE: Remove a Reservation by given id
    public void deleteReservation(String id) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("The reservation ID cannot be empty or null.");
        }
        Reservation reservation = reservationRepository.findReservationById(id);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime reservationTime = reservation.getDateHour();
        if (!reservationTime.isAfter(today)) {
            throw new IllegalStateException("Reservations in the past cannot be canceled.");
        }
        if (today.isAfter(reservationTime.minusMinutes(10)) && today.isBefore(reservationTime.plusMinutes(10))) {
            throw new IllegalStateException("Cannot cancel a reservation that is currently in progress.");
        }
        Reservation r = reservationRepository.findReservationById(id);
        reservationRepository.deleteReservation(r);
        System.out.println("Successfully canceled reservation with ID: " + id);
    }
}


/*

    // GET: Obtain all Reservations
    public List<Reservation> getAllReservations() {

        List<Reservation> reservations = reservationRepository.findAllReservations();
        List<Reservation> weekReservations = new ArrayList<>();
        LocalDate todayDate = LocalDate.now();
        DayOfWeek monday = DayOfWeek.MONDAY;
        LocalDate mondayDate = todayDate.with(monday);
        LocalDate saturdayDate = mondayDate.plusDays(5);
        LocalDateTime mondayDateTime = mondayDate.atStartOfDay();
        LocalDateTime saturdayDateTime = saturdayDate.atTime(LocalTime.MAX);
        for (Reservation reservation : reservations) {

            if (!reservation.getDateHour().isBefore(mondayDateTime)
                    && !reservation.getDateHour().isAfter(saturdayDateTime)) {

                weekReservations.add(reservation);

            }

        }

        return weekReservations;


    }


*/