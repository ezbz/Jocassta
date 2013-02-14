package org.projectx.jocassta.domain;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class QueryRequest extends BasicCassandraRequest implements CassandraRequest {

  private List<String> rowKeys = Collections.emptyList();
  private List<String> columns = Collections.emptyList();

  public List<String> getRowKeys() {
    return rowKeys;
  }

  public void setRowKeys(final List<String> rowKeys) {
    this.rowKeys = rowKeys;
  }

  public List<String> getColumns() {
    return columns;
  }

  public void setColumns(final List<String> columns) {
    this.columns = columns;
  }

  @Override
  public String toString() {
    final ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    tsb.appendSuper(super.toString());
    tsb.append("rowKeys", rowKeys);
    tsb.append("columns", columns);
    return tsb.toString();
  }
}
