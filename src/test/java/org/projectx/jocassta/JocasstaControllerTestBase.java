package org.projectx.jocassta;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

import org.projectx.jocassta.domain.CassandraResult;
import org.projectx.jocassta.domain.CqlQueryRequest;
import org.projectx.jocassta.domain.CqlQueryRequestBuilder;
import org.projectx.jocassta.qeury.JocasstaQueryExecutor;

@ContextConfiguration(loader = TestGenericWebXmlContextLoader.class, locations = { "classpath:applicationContext-Jocassta-all.xml",
                                                                                  "file:src/main/webapp/WEB-INF/spring/Jocassta-servlet.xml" })
public class JocasstaControllerTestBase extends ControllerTestBase {

  @Resource
  private JocasstaQueryExecutor queryExecutor;
  @Value("${org.projectx.cassandra.hosts}")
  protected String host;

  protected List<CassandraResult> executeCql(final String cql) {
    final CqlQueryRequest query = new CqlQueryRequestBuilder().withHosts(host).withCluster("jocassta_test").withKeyspace("jocassta_test")
                                                              .withCqlQuery(cql).build();
    return queryExecutor.executeCql(query);
  }

}