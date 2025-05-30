package org.example.processor;

import java.time.LocalDateTime;
import org.example.alert.AlertReporter;
import org.example.config.LogAnalyzerConfig;
import org.example.model.LogEntry;

public class TimestampGroupProcessor {
  private final LogAnalyzerConfig config;
  private final AlertReporter reporter;

  private LocalDateTime currentGroupTimestamp = null;
  private int totalRequestsInGroup = 0;
  private int failedRequestsInGroup = 0;

  public TimestampGroupProcessor(LogAnalyzerConfig config, AlertReporter reporter) {
    this.config = config;
    this.reporter = reporter;
  }

  public void processEntry(LogEntry entry) {
    if (currentGroupTimestamp == null) {
      startNewGroup(entry);
    } else if (entry.getTimestamp().isEqual(currentGroupTimestamp)) {
      addToCurrentGroup(entry);
    } else {
      finalizeCurrentGroup();
      startNewGroup(entry);
    }
  }

  public void flush() {
    finalizeCurrentGroup();
    reset();
  }

  private void startNewGroup(LogEntry entry) {
    currentGroupTimestamp = entry.getTimestamp();
    totalRequestsInGroup = 1;
    failedRequestsInGroup = isFailed(entry) ? 1 : 0;
  }

  private void addToCurrentGroup(LogEntry entry) {
    totalRequestsInGroup++;
    if (isFailed(entry)) failedRequestsInGroup++;
  }

  private void finalizeCurrentGroup() {
    if (currentGroupTimestamp == null || totalRequestsInGroup == 0) {
      return;
    }

    double availability =
        (totalRequestsInGroup - failedRequestsInGroup) * 100.0 / totalRequestsInGroup;

    if (availability < config.getMinAvailabilityPercent()) {
      reporter.reportAlert(failedRequestsInGroup, currentGroupTimestamp, availability);
    }
  }

  private void reset() {
    currentGroupTimestamp = null;
    totalRequestsInGroup = 0;
    failedRequestsInGroup = 0;
  }

  private boolean isFailed(LogEntry entry) {
    return entry.getHttpStatus() >= 500 || entry.getResponseTime() > config.getMaxResponseTimeMs();
  }
}
