package org.example.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LogAnalyzerConfigTest {

  @Test
  void validConfigShouldBeCreated() {
    LogAnalyzerConfig cfg = new LogAnalyzerConfig(99.5, 100);

    assertEquals(99.5, cfg.getMinAvailabilityPercent());
    assertEquals(100, cfg.getMaxResponseTimeMs());
  }

  @Test
  void invalidAvailabilityShouldThrow() {
    assertThrows(IllegalArgumentException.class, () -> new LogAnalyzerConfig(-1, 100));
    assertThrows(IllegalArgumentException.class, () -> new LogAnalyzerConfig(101, 100));
  }

  @Test
  void invalidResponseTimeShouldThrow() {
    assertThrows(IllegalArgumentException.class, () -> new LogAnalyzerConfig(99, 0));
    assertThrows(IllegalArgumentException.class, () -> new LogAnalyzerConfig(99, -5));
  }
}
