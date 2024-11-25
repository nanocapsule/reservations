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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    Users user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hostelId", nullable = false)
    Hostels hostel;
    @Column(columnDefinition = "DATETIME")
    LocalDateTime checkInTime;
    @Column(columnDefinition = "DATETIME")
    LocalDateTime checkOutTime;
    @Column(precision = 15, scale = 2)
    BigDecimal amount;
    String reservationCode;

    @Override
    public String toString() {
        return "Reservations{" +
            "reservationId=" + reservationId +
            ", hostelId=" + hostel +
            ", user=" + user +
            ", checkInTime=" + checkInTime +
            ", checkOutTime=" + checkOutTime +
            ", amount=" + amount +
            ", reservationCode='" + reservationCode + '\'' +
            '}';
    }
}