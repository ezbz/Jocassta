package org.projectx.jocassta.domain;

public interface CassandraRequest {

  String getCluster();

  String getHostsString();

  String getKeyspace();

  String getColumnFamily();

}
