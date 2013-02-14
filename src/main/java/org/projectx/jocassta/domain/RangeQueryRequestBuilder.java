package org.projectx.jocassta.domain;

import java.util.List;

public class RangeQueryRequestBuilder {

  private final RangeQueryRequest rangeQueryRequest;

  public RangeQueryRequestBuilder() {
    rangeQueryRequest = new RangeQueryRequest();
  }

  public RangeQueryRequest withCluster(final String cluster) {
    rangeQueryRequest.setCluster(cluster);
    return rangeQueryRequest;
  }

  public RangeQueryRequest withKeyspace(final String keyspace) {
    rangeQueryRequest.setKeyspace(keyspace);
    return rangeQueryRequest;
  }

  public RangeQueryRequest withColumnFamily(final String columnFamily) {
    rangeQueryRequest.setColumnFamily(columnFamily);
    return rangeQueryRequest;
  }

  public RangeQueryRequest withRowKeys(final List<String> rowKeys) {
    rangeQueryRequest.setRowKeys(rowKeys);
    return rangeQueryRequest;
  }

  public RangeQueryRequest withColumnRange(final String startColumn, final String endColumn) {
    rangeQueryRequest.setStartColumn(startColumn);
    rangeQueryRequest.setEndColumn(endColumn);
    return rangeQueryRequest;
  }

  public RangeQueryRequest withHosts(final List<String> hosts) {
    rangeQueryRequest.setHosts(hosts);
    return rangeQueryRequest;
  }

  public RangeQueryRequest withLimit(final Integer limit) {
    rangeQueryRequest.setLimit(limit);
    return rangeQueryRequest;
  }

  public RangeQueryRequest withReverseOrder(final boolean reverseOrder) {
    rangeQueryRequest.setReverseOrder(reverseOrder);
    return rangeQueryRequest;
  }
}
