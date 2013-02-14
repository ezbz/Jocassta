package org.projectx.jocassta.domain;

public class ColumnResult {

  private final Object name;
  private final Object value;
  private final Long timestamp;
  private final Integer ttl;

  public ColumnResult(final Object name, final Object value, final Long timestamp, final Integer ttl) {
    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
    this.ttl = ttl;
  }

  public Object getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public Integer getTtl() {
    return ttl;
  }

}
