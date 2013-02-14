package org.projectx.jocassta.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

public class Keyspace {
  private final KeyspaceDefinition keyspaceDefinition;
  private final List<ColumnFamily> columnFamilies = new ArrayList<ColumnFamily>();

  public Keyspace(final KeyspaceDefinition keyspaceDefinition) {
    this.keyspaceDefinition = keyspaceDefinition;
  }

  public String getName() {
    return keyspaceDefinition.getName();
  }

  public String getStrategyClass() {
    return keyspaceDefinition.getStrategyClass();
  }

  public Map<String, String> getStrategyOptions() {
    return keyspaceDefinition.getStrategyOptions();
  }

  public int getReplicationFactor() {
    return keyspaceDefinition.getReplicationFactor();
  }

  public void addColumnFamily(final ColumnFamily columnFamily) {
    columnFamilies.add(columnFamily);
  }

  public List<ColumnFamily> getColumnFamilies() {
    return columnFamilies;
  }

}
