package org.projectx.jocassta.domain;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;

public class BasicCassandraRequest implements CassandraRequest {
  private String cluster;
  private String keyspace;
  private String columnFamily;
  private List<String> hosts = Collections.singletonList("localhost:9160");

  @Override
  public String getCluster() {
    return cluster;
  }

  public void setCluster(final String cluster) {
    this.cluster = cluster;
  }

  @Override
  public String getKeyspace() {
    return keyspace;
  }

  public void setKeyspace(final String keyspace) {
    this.keyspace = keyspace;
  }

  @Override
  public String getColumnFamily() {
    return columnFamily;
  }

  public void setColumnFamily(final String columnFamily) {
    this.columnFamily = columnFamily;
  }

  public List<String> getHosts() {
    return hosts;
  }

  public void setHosts(final List<String> hosts) {
    this.hosts = hosts;
  }

  @Override
  @JsonIgnore
  public String getHostsString() {
    return StringUtils.collectionToCommaDelimitedString(hosts);
  }

  @Override
  public String toString() {
    final ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    tsb.append("hosts", hosts);
    tsb.append("cluster", cluster);
    tsb.append("keyspace", keyspace);
    tsb.append("columnFamily", columnFamily);
    return tsb.toString();
  }
}
