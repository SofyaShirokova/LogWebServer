package org.example.config;

public class LogAnalyzerConfig {
  private final double minAvailabilityPercent;
  private final long maxResponseTimeMs;

  public LogAnalyzerConfig(double minAvailabilityPercent, long maxResponseTimeMs) {

    if (minAvailabilityPercent < 0 || minAvailabilityPercent > 100)
      throw new IllegalArgumentException("Минимальная доступность должна быть между 0 и 100");

    if (maxResponseTimeMs <= 0)
      throw new IllegalArgumentException("Максимальное время ответа должно быть положительным");

    this.minAvailabilityPercent = minAvailabilityPercent;
    this.maxResponseTimeMs = maxResponseTimeMs;
  }

  public double getMinAvailabilityPercent() {
    return minAvailabilityPercent;
  }

  public long getMaxResponseTimeMs() {
    return maxResponseTimeMs;
  }
}
