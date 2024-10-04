package com.hostels.services;

import com.hostels.beans.Reservations;
import com.hostels.repo.ReservationsRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;

    @Transactional
    public Reservations save(Reservations reservation) {
        return reservationsRepository.save(reservation);
    }
}
