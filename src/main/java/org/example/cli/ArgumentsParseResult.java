package org.example.cli;

import org.example.config.LogAnalyzerConfig;

public class ArgumentsParseResult {
  private final LogAnalyzerConfig config;
  private final String filePath;

  public ArgumentsParseResult(LogAnalyzerConfig config, String filePath) {
    this.config = config;
    this.filePath = filePath;
  }

  public LogAnalyzerConfig getConfig() {
    return config;
  }

  public String getFilePath() {
    return filePath;
  }
}
