package org.projectx.jocassta.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class CqlQueryRequestBuilder {

  private final CqlQueryRequest queryRequest;

  public CqlQueryRequestBuilder() {
    queryRequest = new CqlQueryRequest();
  }

  public CqlQueryRequestBuilder withCluster(final String cluster) {
    queryRequest.setCluster(cluster);
    return this;
  }

  public CqlQueryRequestBuilder withKeyspace(final String keyspace) {
    queryRequest.setKeyspace(keyspace);
    return this;
  }

  public CqlQueryRequestBuilder withColumnFamily(final String columnFamily) {
    queryRequest.setColumnFamily(columnFamily);
    return this;
  }

  public CqlQueryRequestBuilder withHosts(final List<String> hosts) {
    queryRequest.setHosts(hosts);
    return this;
  }

  public CqlQueryRequestBuilder withCqlQuery(final String cqlQuery) {
    queryRequest.setCqlQuery(cqlQuery);
    return this;
  }

  public CqlQueryRequestBuilder withHosts(final String hosts) {
    final String[] hostsArray = StringUtils.split(hosts, ",");
    queryRequest.setHosts(Arrays.asList(hostsArray));
    return this;
  }

  public CqlQueryRequestBuilder validate() {
    Assert.notEmpty(queryRequest.getHosts(), "hosts cannot be empty");
    Assert.hasText(queryRequest.getCluster(), "clusterName cannot be empty");
    Assert.hasText(queryRequest.getKeyspace(), "keyspaceName cannot be empty");
    Assert.hasText(queryRequest.getColumnFamily(), "columnFamily cannot be empty");
    return this;
  }

  public CqlQueryRequest build() {
    return queryRequest;
  }
}
