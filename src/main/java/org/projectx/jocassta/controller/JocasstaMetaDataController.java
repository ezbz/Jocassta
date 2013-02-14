package org.projectx.jocassta.controller;

import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.projectx.jocassta.domain.Keyspace;
import org.projectx.jocassta.domain.TokenRangeElement;
import org.projectx.jocassta.transform.HectorDefinitionsTransformer;

@Controller
public class JocasstaMetaDataController {

  private final HectorDefinitionsTransformer transformer = new HectorDefinitionsTransformer();

  @RequestMapping(value = "/keyspaces", method = RequestMethod.GET)
  public @ResponseBody
  List<Keyspace> keyspaces(@RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final Cluster cluster = HFactory.getOrCreateCluster(hosts, hosts);
    return transformer.transformKeyspaceDefs(cluster.describeKeyspaces());
  }

  @RequestMapping(value = "/keyspace/{keyspace}", method = RequestMethod.GET)
  public @ResponseBody
  Keyspace keyspace(@RequestParam(required = false, defaultValue = "localhost:9160") final String hosts, @PathVariable final String keyspace) {
    final Cluster cluster = HFactory.getOrCreateCluster(hosts, hosts);
    return transformer.transformKeyspaceDef(cluster.describeKeyspace(keyspace));
  }

  @RequestMapping(value = "/thrift", method = RequestMethod.GET)
  public @ResponseBody
  String thrift(@RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final Cluster cluster = HFactory.getOrCreateCluster(hosts, hosts);
    return cluster.describeThriftVersion();
  }

  @RequestMapping(value = "/schema/versions", method = RequestMethod.GET)
  public @ResponseBody
  Map<String, List<String>> schemaVersions(@RequestParam(required = false, defaultValue = "localhost:9160") final String hosts) {
    final Cluster cluster = HFactory.getOrCreateCluster(hosts, hosts);
    return cluster.describeSchemaVersions();
  }

  @RequestMapping(value = "/ring", method = RequestMethod.GET)
  public @ResponseBody
  List<TokenRangeElement> ring(@RequestParam(required = false, defaultValue = "localhost:9160") final String hosts,
      @RequestParam final String keyspace) {
    final Cluster cluster = HFactory.getOrCreateCluster(hosts, hosts);
    return transformer.transformTokenRanges(cluster.describeRing(keyspace));
  }
}