
package com.hostels;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.hostels.beans.Reservations;
import com.hostels.services.ReservationsService;
import io.micronaut.function.aws.MicronautRequestHandler;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Inject;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
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
            LocalDateTime checkInTime = LocalDateTime.parse(jsonObject.get("timeIn"));
            LocalDateTime checkOutTime = LocalDateTime.parse(jsonObject.get("timeOut"));
            long hours = Duration.between(checkInTime, checkOutTime).toHours();
            BigDecimal price;
            if(hours == 0)
                throw new RuntimeException("Time range is not allowed.");
            price = BigDecimal.valueOf(6 * hours);
            Reservations reservations = reservationsService.save(
                Reservations
                    .builder()
                    .userId(Long.valueOf(jsonObject.get("user_id")))
                    .hostelId(Long.valueOf(jsonObject.get("hostel_id")))
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