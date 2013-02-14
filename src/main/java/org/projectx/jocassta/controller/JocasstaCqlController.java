package org.projectx.jocassta.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.projectx.jocassta.domain.CassandraResult;
import org.projectx.jocassta.domain.CqlQueryRequest;
import org.projectx.jocassta.domain.CqlQueryRequestBuilder;
import org.projectx.jocassta.qeury.JocasstaQueryExecutor;

@Controller
@RequestMapping("/cql")
public class JocasstaCqlController {
  private String defaultHosts;
  private String defaultClusterName;
  private String defaultKeyspace;

  private JocasstaQueryExecutor jocasstaQueryExecutor;

  public JocasstaCqlController() {
  }

  public JocasstaCqlController(final JocasstaQueryExecutor jocasstaQueryExecutor, final String defaultHosts, final String defaultClusterName,
      final String defaultKeyspace) {
    Assert.notNull(jocasstaQueryExecutor, "jocasstaQueryExecutor cannot be null");
    Assert.hasText(defaultHosts, "defaultHosts cannot be empty");
    Assert.hasText(defaultClusterName, "defaultClusterName cannot be empty");
    Assert.hasText(defaultKeyspace, "defaultKeyspace cannot be empty");
    this.jocasstaQueryExecutor = jocasstaQueryExecutor;
    this.defaultHosts = defaultHosts;
    this.defaultClusterName = defaultClusterName;
    this.defaultKeyspace = defaultKeyspace;
  }

  @RequestMapping(value = "/{cluster}/{keyspace}", method = RequestMethod.GET)
  public @ResponseBody
  List<CassandraResult> execute(@PathVariable final String cluster, @PathVariable final String keyspace, @RequestParam final String query,
      @RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final CqlQueryRequest request = new CqlQueryRequestBuilder().withHosts(hosts).withCluster(cluster).withKeyspace(keyspace).withCqlQuery(query)
                                                                .build();
    return jocasstaQueryExecutor.executeCql(request);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET, params = { "query" })
  public @ResponseBody
  List<CassandraResult> execute(@RequestParam final String query) {
    final CqlQueryRequest request = new CqlQueryRequestBuilder().withHosts(defaultHosts).withCluster(defaultClusterName)
                                                                .withKeyspace(defaultKeyspace).withCqlQuery(query).build();
    return jocasstaQueryExecutor.executeCql(request);
  }

}