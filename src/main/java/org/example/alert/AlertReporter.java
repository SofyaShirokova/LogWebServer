package org.example.alert;

import java.time.LocalDateTime;

public interface AlertReporter {
  void reportAlert(int failedCount, LocalDateTime timestamp, double availability);
}
