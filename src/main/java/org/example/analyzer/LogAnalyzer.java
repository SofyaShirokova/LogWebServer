package org.example.analyzer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.example.alert.AlertReporter;
import org.example.config.LogAnalyzerConfig;
import org.example.model.LogEntry;
import org.example.parser.LogEntryParser;
import org.example.processor.TimestampGroupProcessor;

public class LogAnalyzer {
  private final LogAnalyzerConfig config;
  private final AlertReporter reporter;

  public LogAnalyzer(LogAnalyzerConfig config, AlertReporter reporter) {
    this.config = config;
    this.reporter = reporter;
  }

  public void analyze(String filePath) throws IOException {
    if (filePath == null || !Files.exists(Paths.get(filePath))) {
      throw new FileNotFoundException("Файл не найден или не указан: " + filePath);
    }
    try (FileReader reader = new FileReader(filePath)) {
      analyze(reader);
    }
  }

  public void analyze(Reader reader) throws IOException {
    TimestampGroupProcessor processor = new TimestampGroupProcessor(config, reporter);

    try (BufferedReader br = new BufferedReader(reader)) {
      String line;

      while ((line = br.readLine()) != null) {

        try {
          LogEntry entry = LogEntryParser.parse(line);

          if (entry != null) {
            processor.processEntry(entry);
          }

        } catch (Exception e) {
          System.err.println("Ошибка парсинга: " + e.getMessage());
        }
      }
      processor.flush();
    }
  }
}
