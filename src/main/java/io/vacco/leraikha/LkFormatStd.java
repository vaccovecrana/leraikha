package io.vacco.leraikha;

public enum LkFormatStd {
  Date("date"), // Full-date according to RFC3339.
  Time("time"), // Time with optional time-zone.
  DateTime("date-time"), // date-time from the same source (time-zone is mandatory).
  Duration("duration"), // Duration from RFC3339.
  URI("uri"), // Full URI.
  URIReference("uri-reference"), // URI reference, including full and relative URIs.
  URITemplate("uri-template"), // URI template according to RFC6570.
  // url (deprecated): URL record.
  Email("email"), // Email address.
  Hostname("hostname"), // Host name according to RFC1034.
  Ipv4("ipv4"), // IP address v4.
  Ipv6("ipv6"), // IP address v6.
  Regex("regex"), // Tests whether a string is a valid regular expression by passing it to RegExp constructor.
  UUID("uuid"), // Universally Unique Identifier according to RFC4122.
  JsonPointer("json-pointer"), // JSON-pointer according to RFC6901.
  RelativeJsonPointer("relative-json-pointer") // Relative JSON-pointer according to https://datatracker.ietf.org/doc/html/draft-luff-relative-json-pointer-00
  ;
  public final String format;

  LkFormatStd(String format) {
    this.format = format;
  }
}
