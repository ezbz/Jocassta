package org.projectx.jocassta.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CqlQueryRequest extends BasicCassandraRequest implements CassandraRequest {
  private String cqlQuery;

  public String getCqlQuery() {
    return cqlQuery;
  }

  public void setCqlQuery(final String cqlQuery) {
    this.cqlQuery = cqlQuery;
  }

  @Override
  public String toString() {
    final ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    tsb.appendSuper(super.toString());
    tsb.append("cqlQuery", cqlQuery);
    return tsb.toString();
  }
}
