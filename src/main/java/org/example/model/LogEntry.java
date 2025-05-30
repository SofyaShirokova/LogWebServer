package org.example.model;

import java.time.LocalDateTime;

public class LogEntry {
  private final LocalDateTime timestamp;
  private final int httpStatus;
  private final double responseTime;

  public LogEntry(LocalDateTime timestamp, int httpStatus, double responseTime) {
    this.timestamp = timestamp;
    this.httpStatus = httpStatus;
    this.responseTime = responseTime;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

  public double getResponseTime() {
    return responseTime;
  }
}
