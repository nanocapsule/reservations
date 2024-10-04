package com.hostels.repo;

import com.hostels.beans.Reservations;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservations, Long> {
}