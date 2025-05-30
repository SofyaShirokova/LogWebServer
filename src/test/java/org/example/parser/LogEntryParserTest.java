package org.example.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.example.model.LogEntry;
import org.junit.jupiter.api.Test;

class LogEntryParserTest {

  @Test
  void parsesValidLogLine() {
    String line =
        "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0";
    LogEntry entry = LogEntryParser.parse(line);

    assertNotNull(entry);
    assertEquals(200, entry.getHttpStatus());
    assertEquals(44.510983, entry.getResponseTime());
    assertEquals(LocalDateTime.of(2017, 6, 14, 16, 47, 2), entry.getTimestamp());
  }

  @Test
  void returnsNullOnShortLine() {
    String line = "1 2 3";

    assertNull(LogEntryParser.parse(line));
  }

  @Test
  void throwsOnMalformedLine() {
    String line =
        "192.168.32.181 - - [14/06/201:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0";
    assertThrows(IllegalArgumentException.class, () -> LogEntryParser.parse(line));
  }
}
