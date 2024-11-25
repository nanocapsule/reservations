package com.hostels.beans;

import io.micronaut.configuration.hibernate.jpa.proxy.GenerateProxy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@GenerateProxy
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;

    @Override
    public String toString() {
        return "Users{" +
            "userId=" + userId +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

}