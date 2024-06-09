package br.com.fitnessconsultant.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String checkUpdateDate(LocalDateTime date) {
        return date != null ? formatDate(date) : null;
    }

    public static String formatDate(LocalDateTime date) {
        return date.format(FORMATTER);
    }
}
