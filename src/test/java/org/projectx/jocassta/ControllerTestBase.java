package org.projectx.jocassta;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletContext;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTestBase {
  private static final Logger log = LoggerFactory.getLogger(ControllerTestBase.class);

  public static final String JSON_TYPE = "application/json;charset=UTF-8";

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final ObjectMapper mapper = new ObjectMapper();

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected MockMvc getMvc() {
    return mockMvc;
  }

  protected ServletContext getServletContext() {
    return webApplicationContext.getServletContext();
  }

  protected String toJson(final Object object) throws IOException, JsonGenerationException,
      JsonMappingException {
    if (null == object) {
      return "{}";
    }
    final Writer strWriter = new StringWriter();
    mapper.writeValue(strWriter, object);
    return strWriter.toString();
  }

  protected void printAsJson(final Object entity) {
    if (null == entity) {
      return;
    }
    try {
      log.info(toJson(entity));
    } catch (final Exception e) {
      log.warn("Could not print entity as json: [{}]", entity, e);
    }

  }

  protected void printResult(final ResultActions action) {
    try {
      action.andDo(MockMvcResultHandlers.print());
    } catch (final Exception e) {
      log.error("Error printing action results", e);
    }
  }

  protected ResultActions doGet(final String uri, final Object... urlParams) {
    try {
      final MockHttpServletRequestBuilder requestBuilder = get(uri, urlParams);
      requestBuilder.contentType(MediaType.APPLICATION_JSON);
      final ResultActions action = getMvc().perform(requestBuilder);
      printResult(action);
      return action;
    } catch (final Exception e) {
      throw new IllegalStateException(e);
    }
  }

  protected ResultActions doInBuilder(final RequestBuilderCallback callback, final String uri,
      final Object... urlParams) {
    try {
      final MockHttpServletRequestBuilder requestBuilder = get(uri, urlParams);
      callback.doInBuilder(requestBuilder);
      final ResultActions action = getMvc().perform(requestBuilder);
      printResult(action);
      return action;
    } catch (final Exception e) {
      throw new IllegalStateException(e);
    }
  }

  protected ResultActions doPostJson(final Object entity, final String uri) {
    try {
      printAsJson(entity);
      final MockHttpServletRequestBuilder request = post(uri);
      request.content(toJson(entity).getBytes());
      request.contentType(MediaType.APPLICATION_JSON);
      final ResultActions action = getMvc().perform(request);
      printResult(action);
      return action;
    } catch (final Exception e) {
      throw new IllegalStateException(e);
    }
  }

  protected void assertBasicOkJson(final ResultActions action) throws Exception {
    action.andExpect(status().isOk());
    action.andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

}
