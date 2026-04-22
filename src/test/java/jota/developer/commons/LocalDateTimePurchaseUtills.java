package jota.developer.commons;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimePurchaseUtills {
    public LocalDateTime dateTimePurchase() {
        var dateTime = "2026-04-17T11:01:01.3905248";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
