package edu.eci.cvds.reservas.service;

    import edu.eci.cvds.reservas.model.Reservation;
    import edu.eci.cvds.reservas.repository.reservation.ReservationRepositoryMongo;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
    import org.springframework.boot.test.context.SpringBootTest;

    import java.util.List;
    import java.time.LocalDateTime;
    import java.util.Collections;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    @SpringBootTest
    public class ReservationServiceTest {

        @Mock
        private ReservationRepositoryMongo reservationRepository;

        @InjectMocks
        private ReservationService reservationService;

        private Reservation reservation;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            reservation = new Reservation();
            reservation.setId("ABC123");
            reservation.setLaboratory("Lab A");
            reservation.setDateHour(LocalDateTime.now().plusDays(1));
            reservation.setUser("user1");
            reservation.setClassName("Math");
            reservation.setCreationDate(LocalDateTime.now());
            reservation.setPriority(1);
        }

        @Test
        public void givenOneReservation_whenGetAllReservations_thenSuccess() {
            when(reservationRepository.findAllReservations()).thenReturn(Collections.singletonList(reservation));
            List<Reservation> reservations = reservationService.getAllReservations();
            assertEquals(1, reservations.size());
            assertEquals("ABC123", reservations.get(0).getId());
        }

        @Test
        public void givenNoReservations_whenGetAllReservations_thenNoResults() {
            when(reservationRepository.findAllReservations()).thenReturn(Collections.emptyList());
            List<Reservation> reservations = reservationService.getAllReservations();
            assertTrue(reservations.isEmpty());
        }

        @Test
        public void givenNoReservations_whenCreateReservation_thenSuccess() {
            when(reservationRepository.saveReservation(any(Reservation.class))).thenReturn(reservation);
            Reservation createdReservation = reservationService.saveReservation(reservation);
            assertNotNull(createdReservation);
            assertEquals("ABC123", createdReservation.getId());
        }

        @Test
        public void givenOneReservation_whenDeleteReservation_thenSuccess() {
            when(reservationRepository.findReservationById("ABC123")).thenReturn(reservation);
            doNothing().when(reservationRepository).deleteReservation(reservation);
            reservationService.deleteReservation("ABC123");
            verify(reservationRepository, times(1)).deleteReservation(reservation);
        }

        @Test
        public void givenOneReservation_whenDeleteAndGetAllReservations_thenNoResults() {
            when(reservationRepository.findReservationById("ABC123")).thenReturn(reservation);
            doNothing().when(reservationRepository).deleteReservation(reservation);
            reservationService.deleteReservation("ABC123");
            when(reservationRepository.findAllReservations()).thenReturn(Collections.emptyList());
            List<Reservation> reservations = reservationService.getAllReservations();
            assertTrue(reservations.isEmpty());
        }
    }