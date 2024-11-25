package com.hostels.beans;

import io.micronaut.configuration.hibernate.jpa.proxy.GenerateProxy;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Serdeable
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@GenerateProxy
public class Hostels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hostelId;
    private String hostelName;
    private String hostelAddress;

    @Override
    public String toString() {
        return "Hostels{" +
            "hostelId=" + hostelId +
            ", hostelName='" + hostelName + '\'' +
            ", hostelAddress='" + hostelAddress + '\'' +
            '}';
    }
}