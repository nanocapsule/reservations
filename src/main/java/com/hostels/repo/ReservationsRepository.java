package com.hostels.repo;

import com.hostels.beans.Hostels;
import com.hostels.beans.Reservations;
import com.hostels.beans.Users;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservations, Long> {

    @Query("SELECT r FROM Reservations r JOIN FETCH r.user JOIN FETCH r.hostel WHERE r.checkInTime >= :checkInTime AND r.checkOutTime <= :checkOutTime")
    List<Reservations> findReservationsWithinDateRange(LocalDateTime checkInTime, LocalDateTime checkOutTime);

    @Query("SELECT r FROM Reservations r WHERE " +
        "(:user IS NULL OR r.user = :user) AND " +
        "(:hostel IS NULL OR r.hostel = :hostel) AND " +
        "(:checkInTime IS NULL OR r.checkInTime >= :checkInTime) AND " +
        "(:checkOutTime IS NULL OR r.checkOutTime <= :checkOutTime) AND " +
        "(:reservationCode IS NULL OR r.reservationCode = :reservationCode)")
    List<Reservations> findReservationsByOptionalFields(
        @Nullable Users user,
        @Nullable Hostels hostel,
        @Nullable LocalDateTime checkInTime,
        @Nullable LocalDateTime checkOutTime,
        @Nullable String reservationCode
    );
}