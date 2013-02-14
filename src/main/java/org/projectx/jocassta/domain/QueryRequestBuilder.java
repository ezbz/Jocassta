package org.projectx.jocassta.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class QueryRequestBuilder {

  private final QueryRequest queryRequest;

  public QueryRequestBuilder() {
    queryRequest = new QueryRequest();
  }

  public QueryRequestBuilder withCluster(final String cluster) {
    queryRequest.setCluster(cluster);
    return this;
  }

  public QueryRequestBuilder withKeyspace(final String keyspace) {
    queryRequest.setKeyspace(keyspace);
    return this;
  }

  public QueryRequestBuilder withColumnFamily(final String columnFamily) {
    queryRequest.setColumnFamily(columnFamily);
    return this;
  }

  public QueryRequestBuilder withRowKeys(final String... rowKeys) {
    queryRequest.setRowKeys(Arrays.asList(rowKeys));
    return this;
  }

  public QueryRequestBuilder withRowKeys(final List<String> rowKeys) {
    queryRequest.setRowKeys(rowKeys);
    return this;
  }

  public QueryRequestBuilder withColumns(final List<String> columns) {
    queryRequest.setColumns(columns);
    return this;
  }

  public QueryRequestBuilder withColumns(final String... columns) {
    queryRequest.setColumns(Arrays.asList(columns));
    return this;
  }

  public QueryRequestBuilder withHosts(final List<String> hosts) {
    queryRequest.setHosts(hosts);
    return this;
  }

  public QueryRequestBuilder withHosts(final String hosts) {
    final String[] hostsArray = StringUtils.split(hosts, ",");
    if (null != hostsArray) {
      queryRequest.setHosts(Arrays.asList(hostsArray));
    }
    return this;

  }

  public QueryRequestBuilder validate() {
    Assert.notEmpty(queryRequest.getHosts(), "hosts cannot be empty");
    Assert.hasText(queryRequest.getCluster(), "clusterName cannot be empty");
    Assert.hasText(queryRequest.getKeyspace(), "keyspaceName cannot be empty");
    Assert.hasText(queryRequest.getColumnFamily(), "columnFamily cannot be empty");
    return this;
  }

  public QueryRequest build() {
    return queryRequest;
  }
}
