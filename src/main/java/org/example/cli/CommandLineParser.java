package org.example.cli;

import org.example.config.LogAnalyzerConfig;

public class CommandLineParser {
  public ArgumentsParseResult parse(String[] args) {
    if (args.length != 5) {
      printHelpAndExit();
    }

    if (!args[0].equals("-u")) {
      throw new IllegalArgumentException("Ожидался -u на первой позиции");
    }
    double minAvailability;

    try {
      minAvailability = Double.parseDouble(args[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Некорректное значение для -u: " + args[1]);
    }

    if (!args[2].equals("-t")) {
      throw new IllegalArgumentException("Ожидался -t на третьей позиции");
    }

    long maxResponseTime;

    try {
      maxResponseTime = Long.parseLong(args[3]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Некорректное значение для -t: " + args[3]);
    }

    String filePath = args[4];

    LogAnalyzerConfig config = new LogAnalyzerConfig(minAvailability, maxResponseTime);
    return new ArgumentsParseResult(config, filePath);
  }

  private void printHelpAndExit() {
    System.out.println(
        "Использование: java -jar app.jar -u <доступность> -t <макс_время_ответа> <путь>\n"
            + "  -u <значение>   Минимальная доступность\n"
            + "  -t <значение>   Максимальное время ответа в мс\n"
            + "  <путь>          Путь к лог файлу\n");
    System.exit(0);
  }
}
