package org.projectx.jocassta;

import static org.springframework.test.web.server.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.server.ResultActions;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

public class JocasstaQueryControllerTestIT extends JocasstaControllerTestBase {

  protected String testId;
  protected String columnFamily;

  @Before
  public void before() {
    testId = UUID.randomUUID().toString();
    columnFamily = getClass().getSimpleName() + "_" + StringUtils.replace(testId, "-", "_");
    executeCql(String.format(
        "CREATE TABLE %s (key text PRIMARY KEY) WITH comparator=text AND default_validation=text;",
        columnFamily));
  }

  @After
  public void after() {
    executeCql(String.format("DROP TABLE %s;", columnFamily));
  }

  @Test
  public void testQueryMeta() throws Exception {
    final ResultActions action = doGet("/query/meta");
    assertBasicOkJson(action);
  }

  @Test
  public void testRangeQueryMeta() throws Exception {
    final ResultActions action = doGet("/query/range/meta");
    assertBasicOkJson(action);
  }

  @Test
  public void testQueryColumns() throws Exception {
    executeCql(String.format(
        "INSERT INTO %s (KEY, value) VALUES ('%s', 'test_value') USING TTL 100", columnFamily,
        testId));
    final ResultActions action = doInBuilder(new RequestBuilderCallback() {

      @Override
      public void doInBuilder(final MockHttpServletRequestBuilder requestBuilder) {
        requestBuilder.param("hosts", host);
      }
    }, "/query/{cluster}/{keyspace}/{columnFamily}/{rowKey}/{column}", "jocassta_test",
        "jocassta_test", columnFamily, testId, "value");

    assertBasicOkJson(action);
    action.andExpect(jsonPath("$." + testId).exists());
    action.andExpect(jsonPath("$." + testId + "[0].name").value("value"));
    action.andExpect(jsonPath("$." + testId + "[0].value").value("test_value"));
    action.andExpect(jsonPath("$." + testId + "[0].ttl").value(100));
  }
}
