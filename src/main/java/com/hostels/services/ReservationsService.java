package com.hostels.services;

import com.hostels.beans.Hostels;
import com.hostels.beans.Reservations;
import com.hostels.beans.Users;
import com.hostels.repo.ReservationsRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;

    @Transactional
    public Reservations save(Reservations reservation) {
        return reservationsRepository.save(reservation);
    }

    @Transactional
    public List<Reservations> between(
        LocalDateTime checkIn,
        LocalDateTime checkOut
    ) {
        return reservationsRepository.findReservationsWithinDateRange(checkIn, checkOut);
    }

    public void delete(Long reservationId) {
        reservationsRepository.deleteById(reservationId);
    }

    public List<Reservations> searchReservations(
        Users user,
        Hostels hostel,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        String reservationCode
    ) {
        return reservationsRepository.findReservationsByOptionalFields(
            user, hostel, checkInTime, checkOutTime, reservationCode
        );
    }

}
