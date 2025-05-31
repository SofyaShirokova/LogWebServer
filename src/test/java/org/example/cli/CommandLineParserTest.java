package org.example.cli;

import static org.junit.jupiter.api.Assertions.*;

import org.example.config.LogAnalyzerConfig;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {

  @Test
  void parsesValidArgs() {
    CommandLineParser parser = new CommandLineParser();
    String[] args = {"-u", "99.5", "-t", "200", "log.txt"};
    ArgumentsParseResult result = parser.parse(args);

    assertNotNull(result);
    LogAnalyzerConfig cfg = result.getConfig();

    assertEquals(99.5, cfg.getMinAvailabilityPercent());
    assertEquals(200, cfg.getMaxResponseTimeMs());
    assertEquals("log.txt", result.getFilePath());
  }

  @Test
  void throwsOnInvalidFlags() {
    CommandLineParser parser = new CommandLineParser();

    String[] args = {"-x", "99.1", "-t", "200", "log.txt"};
    assertThrows(IllegalArgumentException.class, () -> parser.parse(args));
  }

  @Test
  void throwsOnInvalidNumbers() {
    CommandLineParser parser = new CommandLineParser();

    String[] args = {"-u", "abcdf", "-t", "200", "log.txt"};
    assertThrows(IllegalArgumentException.class, () -> parser.parse(args));
  }

  @Test
  void throwsOnWrongArgsCount() {
    CommandLineParser parser = new CommandLineParser();

    String[] args = {"-u", "98.7", "-t", "200"};
    assertThrows(IllegalArgumentException.class, () -> parser.parse(args));
  }
}
