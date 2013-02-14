package org.projectx.jocassta.domain;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RangeQueryRequest extends BasicCassandraRequest implements CassandraRequest {
  private List<String> rowKeys = Collections.emptyList();
  private String startColumn;
  private String endColumn;
  private Integer limit = 100;
  private boolean reverseOrder = false;

  public List<String> getRowKeys() {
    return rowKeys;
  }

  public void setRowKeys(final List<String> rowKeys) {
    this.rowKeys = rowKeys;
  }

  public String getStartColumn() {
    return startColumn;
  }

  public void setStartColumn(final String startColumn) {
    this.startColumn = startColumn;
  }

  public String getEndColumn() {
    return endColumn;
  }

  public void setEndColumn(final String endColumn) {
    this.endColumn = endColumn;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(final Integer limit) {
    this.limit = limit;
  }

  public boolean isReverseOrder() {
    return reverseOrder;
  }

  public void setReverseOrder(final boolean reverseOrder) {
    this.reverseOrder = reverseOrder;
  }

  @Override
  public String toString() {
    final ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    tsb.appendSuper(super.toString());
    tsb.append("rowKeys", rowKeys);
    tsb.append("startColumn", startColumn);
    tsb.append("endColumn", endColumn);
    tsb.append("limit", limit);
    return tsb.toString();
  }

}
