package org.example.processor;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.example.alert.AlertReporter;
import org.example.config.LogAnalyzerConfig;
import org.example.model.LogEntry;
import org.junit.jupiter.api.Test;

class TimestampGroupProcessorTest {

  static class TestReporter implements AlertReporter {
    List<String> alerts = new ArrayList<>();

    @Override
    public void reportAlert(int failedCount, LocalDateTime timestamp, double availability) {
      alerts.add(failedCount + " " + timestamp + " " + availability);
    }
  }

  @Test
  void lowAvailabilityCase() {
    LogAnalyzerConfig config = new LogAnalyzerConfig(99.0, 100);
    TestReporter reporter = new TestReporter();
    TimestampGroupProcessor processor = new TimestampGroupProcessor(config, reporter);

    LocalDateTime ts = LocalDateTime.of(2025, 5, 30, 20, 0, 0);
    processor.processEntry(new LogEntry(ts, 200, 50));
    processor.processEntry(new LogEntry(ts, 500, 50));
    processor.flush();

    assertEquals(1, reporter.alerts.size());
    String alert = reporter.alerts.get(0);
    assertTrue(alert.contains("1 2025-05-30T20:00 50.0"));
  }

  @Test
  void noAlertTriggers() {
    LogAnalyzerConfig config = new LogAnalyzerConfig(50.0, 100);
    TestReporter reporter = new TestReporter();
    TimestampGroupProcessor processor = new TimestampGroupProcessor(config, reporter);

    LocalDateTime ts = LocalDateTime.of(2025, 5, 30, 20, 0, 0);
    processor.processEntry(new LogEntry(ts, 200, 50));
    processor.processEntry(new LogEntry(ts, 200, 50));
    processor.flush();

    assertEquals(0, reporter.alerts.size());
  }
}
