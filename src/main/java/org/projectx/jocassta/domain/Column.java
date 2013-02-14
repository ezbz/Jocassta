package org.projectx.jocassta.domain;

import me.prettyprint.hector.api.ddl.ColumnDefinition;

public class Column {
  private final ColumnDefinition columnDefinition;

  public Column(final ColumnDefinition columnDefinition) {
    this.columnDefinition = columnDefinition;
  }

  public String getName() {
    return ByteBufferUtils.string(columnDefinition.getName());
  }

  public String getValidationClass() {
    return columnDefinition.getValidationClass();
  }

  public String getIndexType() {
    return (null == columnDefinition.getIndexType()) ? null : columnDefinition.getIndexType().toString();
  }

  public String getIndexName() {
    return columnDefinition.getIndexName();
  }

}
