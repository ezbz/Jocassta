package org.projectx.jocassta.domain;

import java.util.HashMap;
import java.util.Map;

public class CassandraResult {
  private final Object key;
  private final Map<Object, Object> values = new HashMap<Object, Object>();

  public CassandraResult(final Object key) {
    this.key = key;
  }

  public void addValue(final Object key, final Object value) {
    values.put(key, value);
  }

  public Object getKey() {
    return key;
  }

  public Map<Object, Object> getValues() {
    return values;
  }

}
