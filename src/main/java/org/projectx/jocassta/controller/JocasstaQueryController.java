package org.projectx.jocassta.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.projectx.jocassta.domain.ColumnResult;
import org.projectx.jocassta.domain.QueryRequest;
import org.projectx.jocassta.domain.QueryRequestBuilder;
import org.projectx.jocassta.domain.RangeQueryRequest;
import org.projectx.jocassta.qeury.JocasstaQueryExecutor;

@Controller
@RequestMapping(value = "/query")
public class JocasstaQueryController {
  private final JocasstaQueryExecutor jocasstaQueryExecutor;

  public JocasstaQueryController(final JocasstaQueryExecutor jocasstaQueryExecutor) {
    Assert.notNull(jocasstaQueryExecutor, "jocasstaQueryExecutor cannot be null");
    this.jocasstaQueryExecutor = jocasstaQueryExecutor;
  }

  @RequestMapping(value = "/meta", method = RequestMethod.GET)
  public @ResponseBody
  QueryRequest meta() {
    final QueryRequest queryRequest = new QueryRequest();
    queryRequest.setHosts(Collections.singletonList("localhost:9160"));
    queryRequest.setCluster("test_cluster");
    queryRequest.setKeyspace("my_keyspace");
    queryRequest.setColumnFamily("my_column_family");
    queryRequest.setRowKeys(Arrays.asList("mykey1", "mykey2"));
    queryRequest.setColumns(Arrays.asList("mycol1", "mycol2"));
    return queryRequest;
  }

  @RequestMapping(value = "/range/meta", method = RequestMethod.GET)
  public @ResponseBody
  RangeQueryRequest metaRange() {
    final RangeQueryRequest queryRequest = new RangeQueryRequest();
    queryRequest.setHosts(Collections.singletonList("localhost:9160"));
    queryRequest.setCluster("test_cluster");
    queryRequest.setKeyspace("my_keyspace");
    queryRequest.setColumnFamily("my_column_family");
    queryRequest.setRowKeys(Arrays.asList("mykey1", "mykey2"));
    queryRequest.setStartColumn("mycol1");
    queryRequest.setEndColumn("mycol1");
    return queryRequest;
  }

  @RequestMapping(value = "/{cluster}/{keyspace}/{columnFamily}/{rowKey}/{column}", method = RequestMethod.GET)
  public @ResponseBody
  Map<String, List<ColumnResult>> query(@PathVariable final String cluster, @PathVariable final String keyspace,
      @PathVariable final String columnFamily, @PathVariable final String rowKey, @PathVariable final String column,
      @RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final QueryRequest query = new QueryRequestBuilder().withHosts(hosts).withCluster(cluster).withKeyspace(keyspace).withColumnFamily(columnFamily)
                                                        .withColumns(column).withRowKeys(rowKey).build();
    return jocasstaQueryExecutor.queryColumns(query);
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, List<ColumnResult>> query(final @RequestBody QueryRequest request) {
    return jocasstaQueryExecutor.queryColumns(request);
  }

  @RequestMapping(value = "/range", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, List<ColumnResult>> queryRange(final @RequestBody RangeQueryRequest request) {
    return jocasstaQueryExecutor.queryRange(request);
  }

  @RequestMapping(value = "/{cluster}/{keyspace}/{columnFamily}/{column}", method = RequestMethod.GET)
  public @ResponseBody
  Map<String, List<ColumnResult>> queryMultiple(@PathVariable final String cluster, @PathVariable final String keyspace,
      @PathVariable final String columnFamily, @PathVariable final String column, @RequestParam final String key,
      @RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final String[] keys = StringUtils.split(key, ",");
    final QueryRequest query = new QueryRequestBuilder().withHosts(hosts).withCluster(cluster).withKeyspace(keyspace).withColumnFamily(columnFamily)
                                                        .withColumns(column).withRowKeys(Arrays.asList(keys)).build();
    return jocasstaQueryExecutor.queryColumns(query);
  }

  @RequestMapping(value = "/counter", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, List<ColumnResult>> queryCounter(final @RequestBody QueryRequest request) {
    return jocasstaQueryExecutor.queryCounter(request);
  }

  @RequestMapping(value = "/range/counter", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, List<ColumnResult>> queryCounterRange(final @RequestBody RangeQueryRequest request) {
    return jocasstaQueryExecutor.queryCounterRange(request);
  }

  @RequestMapping(value = "/{cluster}/{keyspace}/{columnFamily}/{column}/counter", method = RequestMethod.GET)
  public @ResponseBody
  Map<String, List<ColumnResult>> queryCounterMultiple(@PathVariable final String cluster, @PathVariable final String keyspace,
      @PathVariable final String columnFamily, @PathVariable final String column, @RequestParam final String key,
      @RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final String[] keys = StringUtils.split(key, ",");
    final QueryRequest query = new QueryRequestBuilder().withHosts(hosts).withCluster(cluster).withKeyspace(keyspace).withColumnFamily(columnFamily)
                                                        .withColumns(column).withRowKeys(Arrays.asList(keys)).build();
    return jocasstaQueryExecutor.queryCounter(query);
  }

  @RequestMapping(value = "/{cluster}/{keyspace}/{columnFamily}/{rowKey}/{column}/counter", method = RequestMethod.GET)
  public @ResponseBody
  Map<String, List<ColumnResult>> queryCounter(@PathVariable final String cluster, @PathVariable final String keyspace,
      @PathVariable final String columnFamily, @PathVariable final String rowKey, @PathVariable final String column,
      @RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final QueryRequest query = new QueryRequestBuilder().withHosts(hosts).withCluster(cluster).withKeyspace(keyspace).withColumnFamily(columnFamily)
                                                        .withColumns(column).withRowKeys(Collections.singletonList(rowKey)).build();
    return jocasstaQueryExecutor.queryCounter(query);
  }

}