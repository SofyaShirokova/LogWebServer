package org.example.alert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ConsoleAlertReporter implements AlertReporter {
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
  private boolean headerPrinted = false;

  @Override
  public void reportAlert(int failedCount, LocalDateTime timestamp, double availability) {
    if (!headerPrinted) {
      System.out.printf("%6s%10s%14s%n", "failures", "timeStamp", "availability");
      headerPrinted = true;
    }

    System.out.printf(
        Locale.US,
        "%6d%11s%10.1f%n",
        failedCount,
        timestamp.toLocalTime().format(TIME_FORMATTER),
        availability);
  }
}
