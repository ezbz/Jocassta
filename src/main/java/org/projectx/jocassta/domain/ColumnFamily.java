package org.projectx.jocassta.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;

public class ColumnFamily {
  private final ColumnFamilyDefinition columnFamilyDefinition;
  private final List<Column> columns = new LinkedList<Column>();

  public ColumnFamily(final ColumnFamilyDefinition columnFamilyDefinition) {
    this.columnFamilyDefinition = columnFamilyDefinition;
  }

  public String getKeyspaceName() {
    return columnFamilyDefinition.getKeyspaceName();
  }

  public String getName() {
    return columnFamilyDefinition.getName();
  }

  public String getColumnType() {
    return (null == columnFamilyDefinition.getColumnType()) ? null : columnFamilyDefinition.getColumnType().getValue();
  }

  public String getComparatorType() {
    return (null == columnFamilyDefinition.getComparatorType()) ? null : columnFamilyDefinition.getComparatorType().getClassName();
  }

  public String getSubComparatorType() {
    return (null == columnFamilyDefinition.getSubComparatorType()) ? null : columnFamilyDefinition.getSubComparatorType().getClassName();
  }

  public String getComparatorTypeAlias() {
    return columnFamilyDefinition.getComparatorTypeAlias();
  }

  public String getSubComparatorTypeAlias() {
    return columnFamilyDefinition.getSubComparatorTypeAlias();
  }

  public String getComment() {
    return columnFamilyDefinition.getComment();
  }

  public double getRowCacheSize() {
    return columnFamilyDefinition.getRowCacheSize();
  }

  public int getRowCacheSavePeriodInSeconds() {
    return columnFamilyDefinition.getRowCacheSavePeriodInSeconds();
  }

  public int getKeyCacheSavePeriodInSeconds() {
    return columnFamilyDefinition.getKeyCacheSavePeriodInSeconds();
  }

  public double getKeyCacheSize() {
    return columnFamilyDefinition.getKeyCacheSize();
  }

  public String getKeyValidationClass() {
    return columnFamilyDefinition.getKeyValidationClass();
  }

  public double getReadRepairChance() {
    return columnFamilyDefinition.getReadRepairChance();
  }

  public void addColumn(final Column column) {
    columns.add(column);
  }

  public List<Column> getColumns() {
    return columns;
  }

  public int getGcGraceSeconds() {
    return columnFamilyDefinition.getGcGraceSeconds();
  }

  public String getDefaultValidationClass() {
    return columnFamilyDefinition.getDefaultValidationClass();
  }

  public int getId() {
    return columnFamilyDefinition.getId();
  }

  public int getMaxCompactionThreshold() {
    return columnFamilyDefinition.getMaxCompactionThreshold();
  }

  public int getMinCompactionThreshold() {
    return columnFamilyDefinition.getMinCompactionThreshold();
  }

  public double getMemtableOperationsInMillions() {
    return columnFamilyDefinition.getMemtableOperationsInMillions();
  }

  public int getMemtableThroughputInMb() {
    return columnFamilyDefinition.getMemtableThroughputInMb();
  }

  public int getMemtableFlushAfterMins() {
    return columnFamilyDefinition.getMemtableFlushAfterMins();
  }

  public boolean isReplicateOnWrite() {
    return columnFamilyDefinition.isReplicateOnWrite();
  }

  public String getCompactionStrategy() {
    return columnFamilyDefinition.getCompactionStrategy();
  }

  public Map<String, String> getCompactionStrategyOptions() {
    return columnFamilyDefinition.getCompactionStrategyOptions();
  }

  public Map<String, String> getCompressionOptions() {
    return columnFamilyDefinition.getCompressionOptions();
  }

  public double getMergeShardsChance() {
    return columnFamilyDefinition.getMergeShardsChance();
  }

  public String getRowCacheProvider() {
    return columnFamilyDefinition.getRowCacheProvider();
  }

  public String getKeyAlias() {
    return (null == columnFamilyDefinition.getKeyAlias()) ? null : ByteBufferUtils.string(columnFamilyDefinition.getKeyAlias());
  }

  public int getRowCacheKeysToSave() {
    return columnFamilyDefinition.getRowCacheKeysToSave();
  }

}
