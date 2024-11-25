
package com.hostels;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.hostels.beans.Hostels;
import com.hostels.beans.Reservations;
import com.hostels.beans.Users;
import com.hostels.services.ReservationsService;
import io.micronaut.function.aws.MicronautRequestHandler;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Inject;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FunctionRequestHandler extends MicronautRequestHandler
    <APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    @Inject
    JsonMapper objectMapper;
    @Inject
    ReservationsService reservationsService;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            Map<String, String> jsonObject = objectMapper.readValue(input.getBody(), Map.class);
            if(input.getHttpMethod().equals("GET")){
                Users user = null;
                if(Objects.nonNull(jsonObject.get("user_id"))){
                    user = Users.builder().userId(Long.valueOf(jsonObject.get("user_id"))).build();
                }
                Hostels hostel = null;
                if(Objects.nonNull(jsonObject.get("hostel_id"))){
                    hostel = Hostels.builder().hostelId(Long.valueOf(jsonObject.get("hostel_id"))).build();
                }
                response.setStatusCode(200);
                response.setBody(reservationsService.searchReservations(
                    user,
                    hostel,
                    Objects.nonNull(jsonObject.get("timeIn")) ? LocalDateTime.parse(jsonObject.get("timeIn")) : null,
                    Objects.nonNull(jsonObject.get("timeOut")) ? LocalDateTime.parse(jsonObject.get("timeOut")) : null,
                    Objects.nonNull(jsonObject.get("reservationCode")) ? jsonObject.get("reservationCode") : null
                ).toString());
                return response;
            }
            if(input.getHttpMethod().equals("DELETE")){
                response.setStatusCode(200);
                if(Objects.isNull(jsonObject.get("reservationId"))) throw new RuntimeException("Reservation is mandatory.");
                reservationsService.delete(
                    Objects.nonNull(jsonObject.get("reservationId")) ? Long.valueOf(jsonObject.get("reservationId")) : null
                );
                response.setBody(String.format("Reservation %s deleted successfully", jsonObject.get("reservationId")));
                return response;
            }
            LocalDateTime checkInTime = LocalDateTime.parse(jsonObject.get("timeIn"));
            LocalDateTime checkOutTime = LocalDateTime.parse(jsonObject.get("timeOut"));
            var existReservation = reservationsService.between(checkInTime, checkOutTime);
            if (!existReservation.isEmpty()) throw new RuntimeException("Capsule already taken.");
            long hours = Duration.between(checkInTime, checkOutTime).toHours();
            BigDecimal price;
            if(hours == 0)
                throw new RuntimeException("Time range is not allowed.");
            price = BigDecimal.valueOf(6 * hours);
            if(Objects.isNull(jsonObject.get("hostel_id"))) throw new RuntimeException("Hostel is mandatory.");
            if(Objects.isNull(jsonObject.get("user_id"))) throw new RuntimeException("User is mandatory.");
            Reservations reservations = reservationsService.save(
                Reservations
                    .builder()
                    .user(Users.builder().userId(Long.valueOf(jsonObject.get("user_id"))).build())
                    .hostel(Hostels.builder().hostelId(Long.valueOf(jsonObject.get("hostel_id"))).build())
                    .checkInTime(checkInTime)
                    .checkOutTime(checkOutTime)
                    .amount(price)
                    .reservationCode(UUID.randomUUID().toString()) //TO DO see how to integrate with the lock.
                    .build()
            );
            response.setStatusCode(200);
            response.setBody(reservations.toString());
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }

}