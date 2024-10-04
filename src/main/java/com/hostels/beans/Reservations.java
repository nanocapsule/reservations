package com.hostels.beans;

import io.micronaut.configuration.hibernate.jpa.proxy.GenerateProxy;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Serdeable
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@GenerateProxy
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reservationId;
    Long userId;
    Long hostelId;
    @Column(columnDefinition = "DATETIME")
    LocalDateTime checkInTime;
    @Column(columnDefinition = "DATETIME")
    LocalDateTime checkOutTime;
    @Column(precision = 15, scale = 2)
    BigDecimal amount;
    String reservationCode;
}