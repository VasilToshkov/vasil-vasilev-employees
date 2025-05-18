package com.example.employees_backend.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
public class LocalDateParser {
    private final static List<DateTimeFormatter> formatters = List.of(
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),         // 2023/05/18
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),         // 2023-05-18
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),         // 18/05/2023
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),         // 18.05.2023
            DateTimeFormatter.BASIC_ISO_DATE
    );

    public LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty() || dateStr.trim().equalsIgnoreCase("null")) {
            return LocalDate.now();
        }

        String trimmed = dateStr.trim();
        for (DateTimeFormatter fmt : formatters) {
            try {
                return LocalDate.parse(trimmed, fmt);
            } catch (Exception ignored) {

            }
        }

        throw new IllegalArgumentException("Unsupported date format: " + dateStr);
    }
}
