package org.example.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.example.model.LogEntry;

public class LogEntryParser {
  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");

  public static LogEntry parse(String line) {
    String[] parts = line.split(" ");

    if (parts.length < 11) {
      return null;
    }

    try {
      String timeStr = parts[3].substring(1);
      LocalDateTime timestamp = LocalDateTime.parse(timeStr, DATE_FORMATTER);

      int httpStatus = Integer.parseInt(parts[8]);
      double responseTime = Double.parseDouble(parts[10]);

      return new LogEntry(timestamp, httpStatus, responseTime);
    } catch (Exception e) {
      throw new IllegalArgumentException("Ошибка парсинга: " + line, e);
    }
  }
}
