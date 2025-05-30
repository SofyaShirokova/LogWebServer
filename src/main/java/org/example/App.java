package org.example;

import org.example.alert.AlertReporter;
import org.example.alert.ConsoleAlertReporter;
import org.example.analyzer.LogAnalyzer;
import org.example.cli.ArgumentsParseResult;
import org.example.cli.CommandLineParser;
import org.example.config.LogAnalyzerConfig;

public class App {
  public static void main(String[] args) {
    try {
      CommandLineParser parser = new CommandLineParser();
      ArgumentsParseResult parseResult = parser.parse(args);

      LogAnalyzerConfig config = parseResult.getConfig();
      String filePath = parseResult.getFilePath();

      AlertReporter reporter = new ConsoleAlertReporter();
      LogAnalyzer analyzer = new LogAnalyzer(config, reporter);

      analyzer.analyze(filePath);

    } catch (Exception e) {
      System.err.println(e.getMessage());

      System.exit(1);
    }
  }
}
